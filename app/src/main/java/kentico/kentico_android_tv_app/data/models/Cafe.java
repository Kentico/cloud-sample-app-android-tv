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
import com.kenticocloud.delivery_core.elements.TextElement;
import com.kenticocloud.delivery_core.elements.models.AssetModel;
import com.kenticocloud.delivery_core.models.item.ContentItem;
import com.kenticocloud.delivery_core.models.item.ElementMapping;

public final class Cafe extends ContentItem {

    public static final String TYPE = "cafe";

    @ElementMapping("city")
    public TextElement city;

    @ElementMapping("street")
    public TextElement street;

    @ElementMapping("country")
    public TextElement country;

    @ElementMapping("state")
    public TextElement state;

    @ElementMapping("zip_code")
    public TextElement zipCode;

    @ElementMapping("phone")
    public TextElement phone;

    @ElementMapping("email")
    public TextElement email;

    @ElementMapping("photo")
    public AssetsElement photo;

    public String getCity() {
        return city.getValue();
    }

    public String getStreet() {
        return street.getValue();
    }

    public String getCountry() {
        return country.getValue();
    }

    public String getState() {
        return state.getValue();
    }

    public String getZipCode() {
        return zipCode.getValue();
    }

    public String getPhone() {
        return phone.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getAddress() { return city.getValue() + ", " + street.getValue(); }

    public String getPhotoUrl() {

        AssetModel[] assets = photo.getValue();

        if (assets == null){
            return null;
        }

        if (assets.length == 0){
            return null;
        }

        return assets[0].url;
    }
}
