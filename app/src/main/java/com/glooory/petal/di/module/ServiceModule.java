package com.glooory.petal.di.module;

import com.glooory.petal.mvp.model.api.service.BoardService;
import com.glooory.petal.mvp.model.api.service.PinService;
import com.glooory.petal.mvp.model.api.service.UserService;

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
    PinService provideHomeService(Retrofit retrofit) {
        return retrofit.create(PinService.class);
    }

    @Singleton
    @Provides
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Singleton
    @Provides
    BoardService provideOperateService(Retrofit retrofit) {
        return retrofit.create(BoardService.class);
    }
}
