package kentico.kentico_android_tv_app;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Juraj on 02.04.2018.
 */

public class ArticleDetailsActivity extends Activity {
    public static final String SHARED_ELEMENT_NAME = "hero";
    public static final String ARTICLE = "Article";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

}
