package kentico.kentico_android_tv_app.data.models;

import com.kenticocloud.delivery_core.elements.AssetsElement;
import com.kenticocloud.delivery_core.elements.NumberElement;
import com.kenticocloud.delivery_core.elements.RichTextElement;
import com.kenticocloud.delivery_core.elements.TextElement;
import com.kenticocloud.delivery_core.elements.models.AssetModel;
import com.kenticocloud.delivery_core.models.item.ContentItem;
import com.kenticocloud.delivery_core.models.item.ElementMapping;

/**
 * Created by Juraj on 06.04.2018.
 */

public class ShopItem extends ContentItem {

    public static final String COFFEE_TYPE = "coffee";
    public static final String BREWER_TYPE = "brewer";
    public static final String GRINDER_TYPE = "grinder";
    public static final String ACCESSORY_TYPE = "accessory";

    public ShopItem(String t) {
        type = t;
    }

    @ElementMapping("product_name")
    public TextElement productName;

    @ElementMapping("price")
    public NumberElement price;

    @ElementMapping("image")
    public AssetsElement image;

    @ElementMapping("short_description")
    public RichTextElement shortDescription;

    public String type;

    public String getProductName() {
        return productName.getValue();
    }

    public double getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        AssetModel[] assets = image.getValue();

        if (assets == null) {
            return null;
        }

        if (assets.length == 0) {
            return null;
        }

        return assets[0].url;
    }

    public String getShortDescription() {
        return shortDescription.getValue();
    }

    public String getType() {
        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }
}
