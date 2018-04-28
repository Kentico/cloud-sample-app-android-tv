package kentico.kentico_android_tv_app.details.cafe;

import android.app.Activity;
import android.os.Bundle;

import kentico.kentico_android_tv_app.R;

/**
 * Created by Juraj on 07.04.2018.
 */

public class CafeDetailsActivity extends Activity {
    public static final String SHARED_ELEMENT_NAME = "CafeDetails";
    public static final String CAFE = "Cafe";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            CafeDetailsFragment fragment = new CafeDetailsFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment, fragment)
                    .commit();
        }
    }
}
