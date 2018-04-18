package kentico.kentico_android_tv_app.utils;

import java.util.Comparator;

import kentico.kentico_android_tv_app.data.models.Article;

public class ArticlesComparator implements Comparator<Article> {
    @Override
    public int compare(Article article1, Article article2) {
        return article2.getPostDate().compareTo(article1.getPostDate());
    }
}
