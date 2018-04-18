package kentico.kentico_android_tv_app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kenticocloud.delivery_core.models.item.ContentItem;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.data.models.Cafe;
import kentico.kentico_android_tv_app.data.models.ShopItem;
import kentico.kentico_android_tv_app.details.article.ArticleDetailsActivity;
import kentico.kentico_android_tv_app.details.cafe.CafeDetailsActivity;
import kentico.kentico_android_tv_app.details.shopItem.ShopItemDetailsActivity;
import kentico.kentico_android_tv_app.presenters.AboutCardPresenter;
import kentico.kentico_android_tv_app.presenters.ArticleCardPresenter;
import kentico.kentico_android_tv_app.presenters.CafeCardPresenter;
import kentico.kentico_android_tv_app.presenters.ShopCardPresenter;

/**
 * Created by Juraj on 25.03.2018.
 */

public class MainFragment extends BrowseFragment {
    private static final int BACKGROUND_UPDATE_DELAY = 300;

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
    private AboutCardPresenter mAboutPresenter = new AboutCardPresenter();
    private ArticleCardPresenter mArticlePresenter = new ArticleCardPresenter();
    private CafeCardPresenter mCafePresenter = new CafeCardPresenter();
    private ShopCardPresenter mShopPresenter = new ShopCardPresenter();

    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private String mBackgroundUri;
    private BackgroundManager mBackgroundManager;

    public static String articleDetailsImageUrl;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareBackgroundManager();

        setupUIElements();

        loadRow(MainApplication.getArticlesList(), mArticlePresenter, R.string.articles);
        loadRow(MainApplication.getShopList(), mShopPresenter, R.string.shop);
        loadRow(MainApplication.getCafesList(), mCafePresenter, R.string.cafes);
        loadRow(MainApplication.getAboutList(), mAboutPresenter, R.string.abouts);

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
        setBadgeDrawable(getActivity().getResources().getDrawable(
         R.drawable.main_logo_border));

        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        setBrandColor(getResources().getColor(R.color.fastlane_background));
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private <T extends ContentItem> void loadRow(List<T> items, Presenter presenter, int headerId) {
        ArrayObjectAdapter rowAdapter = new ArrayObjectAdapter(presenter);
        for (int j = 0; j < items.size(); j++) {
            rowAdapter.add(items.get(j));
        }
        HeaderItem header = new HeaderItem(0, getResources().getString(headerId));
        mRowsAdapter.add(new ListRow(header, rowAdapter));

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

        articleDetailsImageUrl = MainApplication.getAboutList().get(MainApplication.getAboutList().size() - 1).getImageUrl();

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Article) {
                Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);

                int itemIndex = MainApplication.getArticlesList().indexOf(item);
                intent.putExtra(ArticleDetailsActivity.ARTICLE, itemIndex);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        ArticleDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            }

            if (item instanceof ShopItem) {
                Intent intent = new Intent(getActivity(), ShopItemDetailsActivity.class);

                int itemIndex = MainApplication.getShopList().indexOf(item);
                intent.putExtra(ShopItemDetailsActivity.SHOP_ITEM, itemIndex);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        ArticleDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            }

            if (item instanceof Cafe) {
                Intent intent = new Intent(getActivity(), CafeDetailsActivity.class);

                int itemIndex = MainApplication.getCafesList().indexOf(item);
                intent.putExtra(CafeDetailsActivity.CAFE, itemIndex);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        CafeDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
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
}
