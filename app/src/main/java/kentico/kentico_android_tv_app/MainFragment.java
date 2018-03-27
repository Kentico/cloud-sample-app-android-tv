package kentico.kentico_android_tv_app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.util.DisplayMetrics;

import com.kenticocloud.delivery_android.DeliveryAndroidService;
import com.kenticocloud.delivery_core.query.item.MultipleItemQuery;
import com.kenticocloud.delivery_core.services.IDeliveryService;

import java.util.Collections;
import java.util.List;
import java.util.Timer;

import kentico.kentico_android_tv_app.config.AppConfig;
import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.data.source.articles.ArticlesRepository;
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
    private ArrayObjectAdapter mRowsAdapter;
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

        loadRows();
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

    private void loadRows() {
        MultipleItemQuery<Article> articlesQuery = deliveryService.items();

        List<Article> list = articlesQuery.get().getItems();

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();

        int i;
        for (i = 0; i < list.size(); i++) {
            if (i != 0) {
                Collections.shuffle(list);
            }
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            for (int j = 0; j < list.size(); j++) {
                listRowAdapter.add(list.get(j % 5));
            }
            HeaderItem header = new HeaderItem(i, list.get(i).getTitle());
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }

//        HeaderItem gridHeader = new HeaderItem(i, "PREFERENCES");
//
//        GridItemPresenter mGridPresenter = new GridItemPresenter();
//        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
//        gridRowAdapter.add(getResources().getString(R.string.grid_view));
//        gridRowAdapter.add(getString(R.string.error_fragment));
//        gridRowAdapter.add(getResources().getString(R.string.personal_settings));
//        mRowsAdapter.add(new ListRow(gridHeader, gridRowAdapter));

        setAdapter(mRowsAdapter);
    }
}
