package kentico.kentico_android_tv_app.details.article;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kentico.kentico_android_tv_app.R;
import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.utils.ResourceCache;

/**
 * Created by Juraj on 02.04.2018.
 */

public class ArticleDetailsDescription extends Presenter {

    private ResourceCache mResourceCache = new ResourceCache();
    private Context mContext;

    ArticleDetailsDescription(Context context) {
        mContext = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_view_layout, null);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        TextView primaryText = mResourceCache.getViewById(viewHolder.view, R.id.primary_text);
        TextView secondaryText = mResourceCache.getViewById(viewHolder.view, R.id.secondary_text_first);
        TextView extraText = mResourceCache.getViewById(viewHolder.view, R.id.extra_text);

        Article article = (Article) item;
        primaryText.setText(article.getTitle());
        secondaryText.setText(article.getFormattedDate());
        extraText.setText(Html.fromHtml(article.getBodyCopy()));
    }

    @Override public void onUnbindViewHolder(ViewHolder viewHolder) {
    }
}
