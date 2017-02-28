package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.api.service.HomeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Glooory on 17/2/25.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    HomeService provideHomeService(Retrofit retrofit) {
        return retrofit.create(HomeService.class);
    }

}
