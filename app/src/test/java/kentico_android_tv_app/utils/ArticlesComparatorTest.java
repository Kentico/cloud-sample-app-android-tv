package kentico_android_tv_app.utils;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.kenticocloud.delivery_core.elements.DateTimeElement;
import com.kenticocloud.delivery_core.enums.FieldType;
import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.utils.ArticlesComparator;
import org.junit.Test;

public class ArticlesComparatorTest {
    @Test public void articles_are_equals() {
        String dateTimeType = FieldType.date_time.toString();
        TextNode dateNode = new TextNode("2018-10-15T00:00:00");

        Article article1 = new Article();
        article1.postDate = new DateTimeElement(new ObjectMapper(), "post_date", "post_date", dateTimeType, dateNode);

        Article article2 = new Article();
        article2.postDate = new DateTimeElement(new ObjectMapper(), "post_date", "post_date", dateTimeType, dateNode);

        assertEquals(0 /*the same*/, new ArticlesComparator().compare(article1, article2));
    }

    @Test public void articles_are_not_equals() {
        String dateTimeType = FieldType.date_time.toString();
        TextNode dateNode1 = new TextNode("2018-10-15T00:00:00");
        TextNode dateNode2 = new TextNode("2018-10-15T15:00:00");

        Article article1 = new Article();
        article1.postDate = new DateTimeElement(new ObjectMapper(), "post_date", "post_date", dateTimeType, dateNode1);

        Article article2 = new Article();
        article2.postDate = new DateTimeElement(new ObjectMapper(), "post_date", "post_date", dateTimeType, dateNode2);

        assertEquals(1 /*not the same*/, new ArticlesComparator().compare(article1, article2));
    }
}
