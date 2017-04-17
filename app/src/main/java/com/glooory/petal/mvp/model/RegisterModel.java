package com.glooory.petal.mvp.model;

import com.glooory.petal.app.util.StringUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.ui.register.RegisterContract;

import javax.inject.Inject;

import common.BasePetalModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/4/14.
 */

public class RegisterModel extends BasePetalModel<ServiceManager, CacheManager>
        implements RegisterContract.Model {

    @Inject
    public RegisterModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public boolean isPhoneNumber(String number) {
        return StringUtils.isPhoneNumber(number);
    }

    @Override
    public Observable<Void> register(String phoneNumber) {
        return mServiceManager.getUserService()
                .sendIndentifyCode("huaban_android_3.1.3", phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
