package kentico.kentico_android_tv_app.details.about;

import android.app.Activity;
import android.os.Bundle;

import kentico.kentico_android_tv_app.R;

public class AboutDetailsActivity extends Activity {
    public static final String SHARED_ELEMENT_NAME = "AboutDetails";
    public static final String ABOUT = "About";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            AboutDetailsFragment fragment = new AboutDetailsFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment, fragment)
                    .commit();
        }
    }
}
