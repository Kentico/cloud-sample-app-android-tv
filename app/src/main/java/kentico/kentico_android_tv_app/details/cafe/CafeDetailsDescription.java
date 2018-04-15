package kentico.kentico_android_tv_app.details.cafe;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import kentico.kentico_android_tv_app.data.models.Cafe;

/**
 * Created by Juraj on 07.04.2018.
 */

public class CafeDetailsDescription extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Cafe cafe = (Cafe) item;

        if (cafe != null) {
            viewHolder.getTitle().setText(cafe.getState());
            viewHolder.getBody().setText(String.valueOf(cafe.getAddress() + ", " + cafe.getZipCode()));
        }
    }
}
