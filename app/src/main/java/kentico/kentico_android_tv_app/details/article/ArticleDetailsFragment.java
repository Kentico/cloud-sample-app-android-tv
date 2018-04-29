package kentico.kentico_android_tv_app.details.article;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.app.DetailsFragmentBackgroundController;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import kentico.kentico_android_tv_app.MainActivity;
import kentico.kentico_android_tv_app.MainApplication;
import kentico.kentico_android_tv_app.MainFragment;
import kentico.kentico_android_tv_app.R;
import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.details.video.VideoActivity;
import kentico.kentico_android_tv_app.presenters.ArticleCardPresenter;

import static com.bumptech.glide.load.DecodeFormat.PREFER_ARGB_8888;

/**
 * Created by Juraj on 02.04.2018.
 */

public class ArticleDetailsFragment extends DetailsFragment {
    private static final int ACTION_READ_MORE = 1;
    private static final int ACTION_ABOUT_US = 2;
    private static final int ACTION_RETURN_BACK = 3;
    private static final int ACTION_MAIN_MENU = 4;

    private static final int DETAIL_THUMB_WIDTH = 480;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static Article mSelectedArticle;

    private ArrayObjectAdapter mAdapter;
    private ClassPresenterSelector mPresenterSelector;

    private final DetailsFragmentBackgroundController mDetailsBackground
            = new DetailsFragmentBackgroundController(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            String articleTitle = bundle.getString(ArticleDetailsActivity.ARTICLE);
            mSelectedArticle = MainApplication.getArticleByTitle(articleTitle);
        }

        if (mSelectedArticle != null) {
            mPresenterSelector = new ClassPresenterSelector();
            mAdapter = new ArrayObjectAdapter(mPresenterSelector);

            setupDetailsOverviewRowPresenter();

            if (mSelectedArticle.getRelatedArticles().size() > 0) {
                setupRelatedArticleListRow();
            }

            setAdapter(mAdapter);
            initializeBackground();
            setOnItemViewClickedListener(new ItemViewClickedListener());
        } else {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void initializeBackground() {
        mDetailsBackground.enableParallax();
        Glide.with(getActivity())
                .load(MainFragment.beanBagImageUrl)
                .asBitmap()
                .format(PREFER_ARGB_8888)
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
                        row.setImageDrawable(resource);
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size());
                    }
                });

        ArrayObjectAdapter actionAdapter = new ArrayObjectAdapter();

        if (mSelectedArticle.getRelatedArticles().size() > 0) {
            actionAdapter.add(
                    new Action(
                            ACTION_READ_MORE,
                            getResources().getString(R.string.article_read_more)));
        }

        actionAdapter.add(
                new Action(
                        ACTION_ABOUT_US,
                        getResources().getString(R.string.article_about_us)));
        actionAdapter.add(
                new Action(
                        ACTION_RETURN_BACK,
                        getResources().getString(R.string.action_return_back)));
        actionAdapter.add(
                new Action(
                        ACTION_MAIN_MENU,
                        getResources().getString(R.string.action_main_menu)));
        row.setActionsAdapter(actionAdapter);

        mAdapter.add(row);
    }

    private void setupRelatedArticleListRow() {
        List<Article> list = mSelectedArticle.relatedArticles.getValue();

        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new ArticleCardPresenter());
        for (int j = 0; j < list.size(); j++) {
            listRowAdapter.add(list.get(j % 5));
        }

        HeaderItem header = new HeaderItem(0, getString(R.string.related_articles));
        mAdapter.add(new ListRow(header, listRowAdapter));
        mPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
    }

    private void setupDetailsOverviewRowPresenter() {
        FullWidthDetailsOverviewRowPresenter detailsPresenter = new FullWidthDetailsOverviewRowPresenter(
                new ArticleDetailsDescription(getActivity().getApplicationContext())) {

            @Override
            protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
                RowPresenter.ViewHolder viewHolder = super.createRowViewHolder(parent);

                View actionsView = viewHolder.view.
                        findViewById(R.id.details_overview_actions_background);
                actionsView.setBackgroundColor(getActivity().getResources().
                        getColor(R.color.fastlane_background));

                View detailsView = viewHolder.view.findViewById(R.id.details_frame);
                detailsView.setBackgroundColor(
                        getResources().getColor(R.color.selected_background));
                return viewHolder;
            }
        };

        FullWidthDetailsOverviewSharedElementHelper mHelper = new FullWidthDetailsOverviewSharedElementHelper();
        mHelper.setSharedElementEnterTransition(getActivity(), ArticleDetailsActivity.SHARED_ELEMENT_NAME);
        detailsPresenter.setListener(mHelper);
        detailsPresenter.setParticipatingEntranceTransition(false);
        prepareEntranceTransition();

        ListRowPresenter shadowDisabledRowPresenter = new ListRowPresenter();
        shadowDisabledRowPresenter.setShadowEnabled(false);

        ClassPresenterSelector detailsPresenterSelector = new ClassPresenterSelector();
        detailsPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);
        detailsPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());
        mAdapter = new ArrayObjectAdapter(detailsPresenterSelector);

        setupDetailsOverviewRow();

        detailsPresenter.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(Action action) {
                switch ((int) action.getId()) {
                    case ACTION_READ_MORE:
                        setSelectedPosition(1);
                        break;
                    case ACTION_ABOUT_US:
                        Intent intent = new Intent(getActivity(), VideoActivity.class);
                        getActivity().startActivity(intent);

                        break;
                    case ACTION_RETURN_BACK:
                        ArticleDetailsFragment.super.getActivity().onBackPressed();
                        break;
                    case ACTION_MAIN_MENU:
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                }
            }
        });

        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailsPresenter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startEntranceTransition();
            }
        }, 500);
    }

    public int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

            if (item instanceof Article) {
                Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);

                intent.putExtra(ArticleDetailsActivity.ARTICLE, ((Article) item).getTitle());

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
                        ArticleDetailsActivity.SHARED_ELEMENT_NAME).toBundle();
                getActivity().startActivity(intent, bundle);
            }
        }
    }
}