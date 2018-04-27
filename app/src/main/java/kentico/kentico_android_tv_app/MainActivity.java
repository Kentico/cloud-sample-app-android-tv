package kentico.kentico_android_tv_app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Fragment fragment = new MainFragment();
            getFragmentManager().beginTransaction().replace(R.id.main_browse_fragment, fragment)
                    .commit();
        }
    }
}
