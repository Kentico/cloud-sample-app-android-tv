package kentico.kentico_android_tv_app.details.cafe;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kentico.kentico_android_tv_app.R;
import kentico.kentico_android_tv_app.data.models.Cafe;
import kentico.kentico_android_tv_app.utils.ResourceCache;

/**
 * Created by Juraj on 07.04.2018.
 */

public class CafeDetailsDescription extends Presenter {

    private ResourceCache mResourceCache = new ResourceCache();
    private Context mContext;

    CafeDetailsDescription(Context context) {
        mContext = context;
    }

    @Override
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_view_layout, null);
        return new Presenter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        TextView primaryText = mResourceCache.getViewById(viewHolder.view, R.id.primary_text);
        TextView secondaryText = mResourceCache.getViewById(viewHolder.view, R.id.secondary_text_first);
        TextView extraText = mResourceCache.getViewById(viewHolder.view, R.id.extra_text);

        Cafe cafe = (Cafe) item;
        primaryText.setText(cafe.getState());
        secondaryText.setText(String.valueOf(cafe.getAddress() + ", " + cafe.getZipCode()));
        extraText.setText(String.valueOf("Phone: " + cafe.getPhone() +
                System.lineSeparator() +
                System.lineSeparator() +
                "Email: " + cafe.getEmail()));
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
    }

}
