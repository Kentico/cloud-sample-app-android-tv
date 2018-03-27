/*
 * Copyright 2017 Kentico s.r.o. and Richard Sustek
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package kentico.kentico_android_tv_app.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kenticocloud.delivery_android.DeliveryAndroidService;
import com.kenticocloud.delivery_core.config.DeliveryConfig;
import com.kenticocloud.delivery_core.services.IDeliveryService;

import kentico.kentico_android_tv_app.config.AppConfig;
import kentico.kentico_android_tv_app.data.source.articles.ArticlesCloudSource;
import kentico.kentico_android_tv_app.data.source.articles.ArticlesRepository;

/**
 * Enables injection of production implementations at compile time.
 */
public class Injection {

    private static IDeliveryService deliveryService = new DeliveryAndroidService(new DeliveryConfig(AppConfig.KENTICO_CLOUD_PROJECT_ID, AppConfig.getTypeResolvers()));

    public static ArticlesRepository provideArticlesRepository(@NonNull Context context) {
        return ArticlesRepository.getInstance(ArticlesCloudSource.getInstance(context));
    }

//    public static CafesRepository provideCafessRepository(@NonNull Context context) {
//        return CafesRepository.getInstance(CafesCloudSource.getInstance(context));
//    }
//
//    public static CoffeesRepository provideCoffeesRepository(@NonNull Context context) {
//        return CoffeesRepository.getInstance(CoffeesCloudSource.getInstance(context));
//    }

    public static IDeliveryService provideDeliveryService() {
        return Injection.deliveryService;
    }
}