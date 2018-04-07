package kentico.kentico_android_tv_app.details.article;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.text.Html;

import kentico.kentico_android_tv_app.data.models.SerializedArticle;

/**
 * Created by Juraj on 02.04.2018.
 */

public class ArticleDetailsDescription extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        SerializedArticle article = (SerializedArticle) item;

        if (article != null) {
            viewHolder.getTitle().setText(article.getTitle());
            viewHolder.getBody().setText(Html.fromHtml(article.getBodyCopy()));
        }
    }
}
