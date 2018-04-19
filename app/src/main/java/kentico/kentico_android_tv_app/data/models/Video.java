package kentico.kentico_android_tv_app.data.models;

import com.kenticocloud.delivery_core.elements.MultipleChoiceElement;
import com.kenticocloud.delivery_core.elements.TextElement;
import com.kenticocloud.delivery_core.models.item.ContentItem;
import com.kenticocloud.delivery_core.models.item.ElementMapping;

public class Video extends ContentItem {

    public static final String TYPE = "video_host";

    @ElementMapping("video_id")
    public TextElement id;

    @ElementMapping("video_host")
    public MultipleChoiceElement host;

    public String getId() {
        return id.getValue();
    }

    public String getHost() {
        return host.getValue()[0].codename;
    }
}
