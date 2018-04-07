package kentico.kentico_android_tv_app.details.article;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import kentico.kentico_android_tv_app.MainActivity;
import kentico.kentico_android_tv_app.MainApplication;
import kentico.kentico_android_tv_app.R;
import kentico.kentico_android_tv_app.data.models.Article;

/**
 * Created by Juraj on 02.04.2018.
 */

public class ArticleDetailsFragment extends DetailsFragment {
    private static final String TAG = "VideoDetailsFragment";

    private static final int ACTION_READ_MORE = 1;
    private static final int ACTION_ABOUT_US = 2;
    private static final int ACTION_RETURN_BACK = 3;

    private static final int DETAIL_THUMB_WIDTH = 480;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static final int NUM_COLS = 10;

    private Article mSelectedArticle;

    private ArrayObjectAdapter mAdapter;
    private ClassPresenterSelector mPresenterSelector;

    private DetailsFragmentBackgroundController mDetailsBackground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);

        mDetailsBackground = new DetailsFragmentBackgroundController(this);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            int articleIndex = bundle.getInt(ArticleDetailsActivity.ARTICLE);
            mSelectedArticle = MainApplication.getArticlesList().get(articleIndex);
        }

        if (mSelectedArticle != null) {
            mPresenterSelector = new ClassPresenterSelector();
            mAdapter = new ArrayObjectAdapter(mPresenterSelector);
            setupDetailsOverviewRow();
            setupDetailsOverviewRowPresenter();
//            setupRelatedArticleListRow();
            setAdapter(mAdapter);
            initializeBackground(mSelectedArticle);
            setOnItemViewClickedListener(new ItemViewClickedListener());
        } else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void initializeBackground(Article data) {
        mDetailsBackground.enableParallax();
        Glide.with(getActivity())
                .load(data.getTeaserImageUrl())
                .asBitmap()
                .centerCrop()
                .error(R.drawable.default_background)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                GlideAnimation<? super Bitmap> glideAnimation) {
                        mDetailsBackground.setCoverBitmap(bitmap);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }
                });
    }

    private void setupDetailsOverviewRow() {
        Log.d(TAG, "doInBackground: " + mSelectedArticle.toString());
        final DetailsOverviewRow row = new DetailsOverviewRow(mSelectedArticle);
        row.setImageDrawable(
                ContextCompat.getDrawable(getActivity(), R.drawable.default_background));
        int width = convertDpToPixel(getActivity().getApplicationContext(), DETAIL_THUMB_WIDTH);
        int height = convertDpToPixel(getActivity().getApplicationContext(), DETAIL_THUMB_HEIGHT);
        Glide.with(getActivity())
                .load(mSelectedArticle.getTeaserImageUrl())
                .centerCrop()
                .error(R.drawable.default_background)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        Log.d(TAG, "details overview card image url ready: " + resource);
                        row.setImageDrawable(resource);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }
                });

        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();

        actionAdapter.add(
                new Action(
                        ACTION_READ_MORE,
                        getResources().getString(R.string.article_read_more)));
        actionAdapter.add(
                new Action(
                        ACTION_ABOUT_US,
                        getResources().getString(R.string.article_about_us)));
        actionAdapter.add(
                new Action(
                        ACTION_RETURN_BACK,
                        getResources().getString(R.string.action_return_back)));
        row.setActionsAdapter(actionAdapter);

        mAdapter.add(row);
    }

    private void setupDetailsOverviewRowPresenter() {
        // Set detail background.
        FullWidthDetailsOverviewRowPresenter detailsPresenter =
                new FullWidthDetailsOverviewRowPresenter(new ArticleDetailsDescription());
        detailsPresenter.setBackgroundColor(
                ContextCompat.getColor(getActivity(), R.color.selected_background));

        // Hook up transition element.
        FullWidthDetailsOverviewSharedElementHelper sharedElementHelper =
                new FullWidthDetailsOverviewSharedElementHelper();
        sharedElementHelper.setSharedElementEnterTransition(
                getActivity(), ArticleDetailsActivity.SHARED_ELEMENT_NAME);
        detailsPresenter.setListener(sharedElementHelper);
        detailsPresenter.setParticipatingEntranceTransition(true);

        detailsPresenter.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(Action action) {
                if (action.getId() == ACTION_RETURN_BACK) {
                    ArticleDetailsFragment.super.getActivity().onBackPressed();
                }
            }
        });

        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
    }

//    private void setupRelatedArticleListRow() {
//        String subcategories[] = {getString(R.string.related_movies)};
//        List<Article> list = List.getList();
//
//        Collections.shuffle(list);
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
//        for (int j = 0; j < NUM_COLS; j++) {
//            listRowAdapter.add(list.get(j % 5));
//        }
//
//        HeaderItem header = new HeaderItem(0, subcategories[0]);
//        mAdapter.add(new ListRow(header, listRowAdapter));
//        mPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
//    }

    public int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Article) {
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
                try {
                    int itemIndex = MainApplication.getArticlesList().indexOf(item);
                    intent.putExtra(ArticleDetailsActivity.ARTICLE, itemIndex);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                 ArticleDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            }
        }
    }
}
