package kentico.kentico_android_tv_app.details.shopItem;

import android.app.Activity;
import android.os.Bundle;

import kentico.kentico_android_tv_app.R;

public class ShopItemDetailsActivity extends Activity {
    public static final String SHARED_ELEMENT_NAME = "ShopItemDetails";
    public static final String SHOP_ITEM = "Shop Item";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            ShopItemDetailsFragment fragment = new ShopItemDetailsFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment, fragment)
                    .commit();
        }
    }
}
