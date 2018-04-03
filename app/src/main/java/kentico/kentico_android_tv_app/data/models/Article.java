/*
 * Copyright 2017 Kentico s.r.o. and Richard Sustek
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package kentico.kentico_android_tv_app.data.models;

import com.kenticocloud.delivery_core.elements.AssetsElement;
import com.kenticocloud.delivery_core.elements.DateTimeElement;
import com.kenticocloud.delivery_core.elements.RichTextElement;
import com.kenticocloud.delivery_core.elements.TextElement;
import com.kenticocloud.delivery_core.elements.models.AssetModel;
import com.kenticocloud.delivery_core.models.item.ContentItem;
import com.kenticocloud.delivery_core.models.item.ElementMapping;

import java.util.Date;

public final class Article extends ContentItem {

    public static final String TYPE = "article";

    @ElementMapping("summary")
    public TextElement summary;

    @ElementMapping("title")
    public TextElement title;

    @ElementMapping("teaser_image")
    public AssetsElement teaserImage;

    @ElementMapping("body_copy")
    public RichTextElement bodyCopy;

    @ElementMapping("post_date")
    public DateTimeElement postDate;

    //    @ElementMapping("personas")
//    public TaxonomyElement personas;

//    @ElementMapping("related_articles")
//    public ModularContentElement<Article> relatedArticles;

    public String getTitle() {
        return title.getValue();
    }

    public String getTeaserImageUrl(){
        AssetModel[] assets = this.teaserImage.getValue();
        if (assets == null){
            return null;
        }

        if (assets.length == 0){
            return null;
        }

        return assets[0].url;
    }

    public Date getPostDate() {
        return postDate.getValue();
    }

    public String getSummary() {
        return summary.getValue();
    }

    public String getBodyCopy() {
        return bodyCopy.getValue();
    }

//    public ElementsTaxonomyTerms[] getPersonas() { return personas.getValue(); }

//    public ArrayList<Article> getRelatedArticles() { return relatedArticles.getValue(); }
}
