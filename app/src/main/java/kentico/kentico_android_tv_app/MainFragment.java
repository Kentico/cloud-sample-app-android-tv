package kentico.kentico_android_tv_app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kenticocloud.delivery_core.query.item.MultipleItemQuery;
import com.kenticocloud.delivery_core.services.IDeliveryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.data.models.Cafe;
import kentico.kentico_android_tv_app.data.models.SerializedArticle;
import kentico.kentico_android_tv_app.injection.Injection;

/**
 * Created by Juraj on 25.03.2018.
 */

public class MainFragment extends BrowseFragment {
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 15;

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    private IDeliveryService deliveryService = Injection.provideDeliveryService();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareBackgroundManager();

        setupUIElements();

        new ArticlesConnection().execute();
        new CafesConnection().execute();

        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        setTitle(getString(R.string.browse_title));

        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);


        setBrandColor(getResources().getColor(R.color.fastlane_background));
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void loadArticlesRow(List<Article> articles) {
        CardPresenter cardPresenter = new CardPresenter();

        ArrayObjectAdapter articlesRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (int j = 0; j < articles.size(); j++) {
            articlesRowAdapter.add(articles.get(j));
        }
        HeaderItem header = new HeaderItem(0, getResources().getString(R.string.articles));
        mRowsAdapter.add(new ListRow(header, articlesRowAdapter));

//
//        HeaderItem gridHeader = new HeaderItem(0, "PREFERENCES");
//
//        GridItemPresenter mGridPresenter = new GridItemPresenter();
//        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
//        gridRowAdapter.add("view");
//        gridRowAdapter.add("error");
//        gridRowAdapter.add("settings or whatever");
//        mRowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

//        setAdapter(mRowsAdapter);
    }

    private void loadCafesRow(List<Cafe> cafes) {
        CardPresenter cardPresenter = new CardPresenter();

        ArrayObjectAdapter cafesRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (int j = 0; j < cafes.size(); j++) {
            cafesRowAdapter.add(cafes.get(j));
        }
        HeaderItem header = new HeaderItem(0, getResources().getString(R.string.cafes));
        mRowsAdapter.add(new ListRow(header, cafesRowAdapter));

        setAdapter(mRowsAdapter);
    }


    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Article) {
                Article article = (Article) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);

                SerializedArticle serializedArticle = new SerializedArticle(article);
                intent.putExtra(ArticleDetailsActivity.ARTICLE, serializedArticle);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        ArticleDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            } else if (item instanceof String) {
                if (((String) item).contains(getString(R.string.error_fragment))) {
                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Article) {
                mBackgroundUri = ((Article) item).getTeaserImageUrl();
                startBackgroundTimer();
            }
            if (item instanceof Cafe) {
                mBackgroundUri = ((Cafe) item).getPhotoUrl();
                startBackgroundTimer();
            }
        }
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateBackground(mBackgroundUri);
                }
            });
        }
    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private class ArticlesConnection extends AsyncTask<Object, Object, List<Article>> {

        @Override
        protected List<Article> doInBackground(Object... arg0) {
            List<Article> list = new ArrayList<>();

            try {
                MultipleItemQuery<Article> articlesQuery = deliveryService.<Article>items().type(Article.TYPE);
                list = articlesQuery.get().getItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Article> list) {
            loadArticlesRow(list);
        }

    }

    private class CafesConnection extends AsyncTask<Object, Object, List<Cafe>> {

        @Override
        protected List<Cafe> doInBackground(Object... arg0) {
            List<Cafe> list = new ArrayList<>();

            try {
                MultipleItemQuery<Cafe> cafesQuery = deliveryService.<Cafe>items().type(Cafe.TYPE);
                list = cafesQuery.get().getItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Cafe> list) {
            loadCafesRow(list);
        }
    }
}
