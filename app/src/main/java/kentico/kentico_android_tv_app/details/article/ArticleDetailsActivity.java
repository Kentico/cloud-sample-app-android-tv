package kentico.kentico_android_tv_app.details.article;

import android.app.Activity;
import android.os.Bundle;

import kentico.kentico_android_tv_app.R;

/**
 * Created by Juraj on 02.04.2018.
 */

public class ArticleDetailsActivity extends Activity {
    public static final String SHARED_ELEMENT_NAME = "ArticleDetails";
    public static final String BACKGROUND_IMAGE = "Background";
    public static final String ARTICLE = "Article";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

}
