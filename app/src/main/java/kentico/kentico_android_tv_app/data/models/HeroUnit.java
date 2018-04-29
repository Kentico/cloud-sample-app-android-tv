package kentico.kentico_android_tv_app.data.models;

import com.kenticocloud.delivery_core.elements.AssetsElement;
import com.kenticocloud.delivery_core.elements.TextElement;
import com.kenticocloud.delivery_core.elements.models.AssetModel;
import com.kenticocloud.delivery_core.models.item.ContentItem;
import com.kenticocloud.delivery_core.models.item.ElementMapping;

public class HeroUnit extends ContentItem {

    public static final String TYPE = "hero_unit";

    @ElementMapping("title")
    public TextElement title;

    @ElementMapping("image")
    public AssetsElement image;

    public String getTitle() {
        return title.getValue();
    }

    public String getImageUrl(){
        AssetModel[] assets = this.image.getValue();
        if (assets == null){
            return null;
        }

        if (assets.length == 0){
            return null;
        }

        return assets[0].url;
    }
}
