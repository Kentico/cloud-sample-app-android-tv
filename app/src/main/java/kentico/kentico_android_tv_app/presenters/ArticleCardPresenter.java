package kentico.kentico_android_tv_app.presenters;

/**
 * Created by Juraj on 27.03.2018.
 */

import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kentico.kentico_android_tv_app.R;
import kentico.kentico_android_tv_app.data.models.Article;

public class ArticleCardPresenter extends Presenter {
    private static final int CARD_WIDTH = 400;
    private static final int CARD_HEIGHT = 176;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;

        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sDefaultBackgroundColor = parent.getResources().getColor(R.color.default_background);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.selected_background);

        ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);

        ((TextView) cardView.findViewById(R.id.title_text)).setTextColor(parent.getResources().getColor(R.color.card_text));
        ((TextView) cardView.findViewById(R.id.content_text)).setTextColor(parent.getResources().getColor(R.color.card_text));

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        if (item.getClass() == Article.class) {
            Article article = (Article) item;
            ImageCardView cardView = (ImageCardView) viewHolder.view;

            if (article.teaserImage != null) {
                cardView.setTitleText(article.getTitle());
                cardView.setContentText(article.getFormattedDate());
                cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
                Glide.with(viewHolder.view.getContext())
                        .load(article.getTeaserImageUrl())
                        .centerCrop()
                        .into(cardView.getMainImageView());
            }
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}

