package kentico.kentico_android_tv_app.details.video;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import kentico.kentico_android_tv_app.R;

/**
 * Activity that hosts VideoConsumptionExampleFragment.
 */
public class VideoActivity extends Activity {

    public static final String TAG = "VideoActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.videoFragment, new VideoConsumptionFragment(),
                    VideoConsumptionFragment.TAG);
            ft.commit();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

}
