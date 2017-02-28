package com.glooory.petal.di.module;


import com.glooory.petal.mvp.model.api.cache.HomeCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;

/**
 * Created by Glooory on 25/2/2017.
 */
@Module
public class CacheModule {

    @Singleton
    @Provides
    HomeCache provideHomeCache(RxCache rxCache) {
        return rxCache.using(HomeCache.class);
    }

}
