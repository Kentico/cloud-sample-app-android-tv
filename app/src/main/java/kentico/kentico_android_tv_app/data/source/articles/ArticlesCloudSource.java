/*
 * Copyright 2017 Kentico s.r.o. and Richard Sustek
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package kentico.kentico_android_tv_app.data.source.articles;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kenticocloud.delivery_core.callbacks.BaseCloudSource;
import com.kenticocloud.delivery_core.models.item.DeliveryItemListingResponse;
import com.kenticocloud.delivery_core.models.item.DeliveryItemResponse;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kentico.kentico_android_tv_app.data.models.Article;
import kentico.kentico_android_tv_app.injection.Injection;

public class ArticlesCloudSource extends BaseCloudSource implements ArticlesDataSource {

    private static ArticlesCloudSource INSTANCE;

    // Prevent direct instantiation.
    private ArticlesCloudSource(@NonNull Context context) {
        super(Injection.provideDeliveryService());
    }

    public static ArticlesCloudSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ArticlesCloudSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getArticles(@NonNull final LoadArticlesCallback callback) {
        this.deliveryService.<Article>items()
                .type(Article.TYPE)
                .getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeliveryItemListingResponse<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DeliveryItemListingResponse<Article> response) {
                        List<Article> items = (response.getItems());

                        if (items == null || items.size() == 0){
                            callback.onDataNotAvailable();
                            return;
                        }

                        callback.onItemsLoaded(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getArticle(@NonNull String codename, @NonNull final LoadArticleCallback callback) {
        this.deliveryService.<Article>item(codename)
                .getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeliveryItemResponse<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DeliveryItemResponse<Article> response) {
                        if (response.getItem() == null){
                            callback.onDataNotAvailable();
                        }

                        callback.onItemLoaded(response.getItem());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
