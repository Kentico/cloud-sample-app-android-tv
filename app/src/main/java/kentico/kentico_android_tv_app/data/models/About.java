package kentico.kentico_android_tv_app.data.models;

import com.kenticocloud.delivery_core.elements.AssetsElement;
import com.kenticocloud.delivery_core.elements.RichTextElement;
import com.kenticocloud.delivery_core.elements.TextElement;
import com.kenticocloud.delivery_core.elements.models.AssetModel;
import com.kenticocloud.delivery_core.models.item.ContentItem;
import com.kenticocloud.delivery_core.models.item.ElementMapping;

/**
 * Created by Juraj on 04.04.2018.
 */

public final class About extends ContentItem {

    public static final String TYPE = "fact_about_us";

    @ElementMapping("title")
    public TextElement title;

    @ElementMapping("description")
    public RichTextElement description;

    @ElementMapping("image")
    public AssetsElement image;

    public String getTitle() {
        return title.getValue();
    }

    public String getDescription() {
        return description.getValue();
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
