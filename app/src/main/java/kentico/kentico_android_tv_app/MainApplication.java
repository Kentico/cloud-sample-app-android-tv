package kentico.kentico_android_tv_app;

import android.app.Application;
import android.os.AsyncTask;

import com.kenticocloud.delivery_core.models.item.ContentItem;
import com.kenticocloud.delivery_core.query.item.MultipleItemQuery;
import com.kenticocloud.delivery_core.services.IDeliveryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kentico.kentico_android_tv_app.data.models.About;
import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.data.models.Cafe;
import kentico.kentico_android_tv_app.data.models.ShopItem;
import kentico.kentico_android_tv_app.injection.Injection;
import kentico.kentico_android_tv_app.utils.ArticlesComparator;

/**
 * Created by Juraj on 07.04.2018.
 */

public class MainApplication extends Application {

    private static IDeliveryService deliveryService = Injection.provideDeliveryService();

    private static List<Article> articlesList;
    private static List<Cafe> cafesList;
    private static List<About> aboutList;
    private static List<ShopItem> shopList;

    public void onCreate() {
        super.onCreate();

        try {
            articlesList = copyList(new DefaultConnection<Article>().execute(Article.TYPE).get());
            Collections.sort(articlesList, new ArticlesComparator());

            shopList = copyList(new DefaultConnection<ShopItem>().execute(ShopItem.COFFEE_TYPE).get());
            shopList.addAll(new DefaultConnection<ShopItem>().execute(ShopItem.BREWER_TYPE).get());
            shopList.addAll(new DefaultConnection<ShopItem>().execute(ShopItem.GRINDER_TYPE).get());
            shopList.addAll(new DefaultConnection<ShopItem>().execute(ShopItem.ACCESSORY_TYPE).get());

            cafesList = copyList(new DefaultConnection<Cafe>().execute(Cafe.TYPE).get());

            aboutList = copyList(new DefaultConnection<About>().execute(About.TYPE).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Article> getArticlesList() {
        return articlesList;
    }

    public static List<Cafe> getCafesList() {
        return cafesList;
    }

    public static List<About> getAboutList() {
        return aboutList;
    }

    public static List<ShopItem> getShopList() {
        return shopList;
    }

    private static class DefaultConnection<T extends ContentItem> extends AsyncTask<String, Object, List<T>> {

        @Override
        protected List<T> doInBackground(String... arg0) {
            List<T> list = new ArrayList<>();

            try {
                MultipleItemQuery<T> query;

                switch (arg0[0]) {
                    case Cafe.TYPE:
                        query = deliveryService.<T>items().type(arg0[0]).equalsFilter("elements.country", "USA");
                        break;
                    default:
                        query = deliveryService.<T>items().type(arg0[0]);
                }

                list = query.get().getItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    private static <T> List<T> copyList(List<T> source) {
        return new ArrayList<>(source);
    }

    public static Article getArticleByTitle(String title) {
        for (Article a: articlesList) {
            if (a.getTitle().equals(title)) {
                return a;
            }
        }

        return articlesList.get(0);
    }
}
