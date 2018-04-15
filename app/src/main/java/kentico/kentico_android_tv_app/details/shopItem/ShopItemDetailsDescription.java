package kentico.kentico_android_tv_app.details.shopItem;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import kentico.kentico_android_tv_app.data.models.ShopItem;

public class ShopItemDetailsDescription extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        ShopItem shopItem = (ShopItem) item;

        if (shopItem != null) {
            viewHolder.getTitle().setText(String.valueOf(shopItem.getType() + ": " + shopItem.getProductName()));
            viewHolder.getBody().setText(String.valueOf("Price: " + shopItem.getPrice() + " $"));
        }
    }
}
