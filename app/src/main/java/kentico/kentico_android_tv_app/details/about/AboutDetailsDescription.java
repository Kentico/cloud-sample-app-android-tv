package kentico.kentico_android_tv_app.details.about;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kentico.kentico_android_tv_app.R;
import kentico.kentico_android_tv_app.data.models.About;
import kentico.kentico_android_tv_app.utils.ResourceCache;

public class AboutDetailsDescription extends Presenter {

    private ResourceCache mResourceCache = new ResourceCache();
    private Context mContext;

    AboutDetailsDescription(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_view_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        TextView primaryText = mResourceCache.getViewById(viewHolder.view, R.id.primary_text);
        TextView extraText = mResourceCache.getViewById(viewHolder.view, R.id.extra_text);

        About aboutItem = (About) item;
        primaryText.setText(aboutItem.getTitle());
        extraText.setText(Html.fromHtml(aboutItem.getDescription()));
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
    }
}
