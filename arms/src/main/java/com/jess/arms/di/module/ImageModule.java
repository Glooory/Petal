package com.jess.arms.di.module;

import com.jess.arms.widget.imageloader.BaseImageLoaderStrategy;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.fresco.FrescoImageLoaderStrategy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jess on 8/5/16 16:10
 * contact with jess.yan.effort@gmail.com
 */
@Module
public class ImageModule {

//    @Singleton
//    @Provides
//    public BaseImageLoaderStrategy provideImageLoaderStrategy(GlideImageLoaderStrategy glideImageLoaderStrategy) {
//        return glideImageLoaderStrategy;
//    }

    @Singleton
    @Provides
    public BaseImageLoaderStrategy provideImageLoaderStrategy() {
        return new FrescoImageLoaderStrategy();
    }

    @Singleton
    @Provides
    public ImageLoader provideImageLoader(BaseImageLoaderStrategy imageLoaderStrategy) {
        return new ImageLoader(imageLoaderStrategy);
    }

}
