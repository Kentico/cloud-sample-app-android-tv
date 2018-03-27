package kentico.kentico_android_tv_app;

import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import com.kenticocloud.delivery_android.DeliveryAndroidService;
import com.kenticocloud.delivery_core.services.IDeliveryService;

/**
 * Created by Juraj on 25.03.2018.
 */

public class MainFragment extends BrowseFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        IDeliveryService androidDeliveryService = new DeliveryAndroidService();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
