package com.glooory.petal.mvp.model;

import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.ui.collect.CollectContract;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import common.BasePetalModel;

/**
 * Created by Glooory on 17/4/3.
 */
@ActivityScope
public class CollectModel extends BasePetalModel<ServiceManager, CacheManager>
        implements CollectContract.Model {

    @Inject
    public CollectModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }


}
