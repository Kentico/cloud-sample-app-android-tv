package kentico.kentico_android_tv_app.data.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Juraj on 03.04.2018.
 */

public class SerializedArticle implements Serializable {
    public static final String TYPE = "article";

    private String summary;
    private String title;
    private String teaserImageUrl;
    private String bodyCopy;
    private Date postDate;

    public SerializedArticle(Article article) {
        this.summary = article.getSummary();
        this.title = article.getTitle();
        this.teaserImageUrl = article.getTeaserImageUrl();
        this.bodyCopy = article.getBodyCopy();
        this.postDate = article.getPostDate();
    }

    public String getSummary() {
        return summary;
    }

    public String getTitle() {
        return title;
    }

    public String getTeaserImageUrl() {
        return teaserImageUrl;
    }

    public String getBodyCopy() {
        return bodyCopy;
    }

    public Date getPostDate() {
        return postDate;
    }
}
