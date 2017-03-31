package com.glooory.petal.mvp.model;

import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.ui.search.result.SearchResultContract;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import common.BasePetalModel;

/**
 * Created by Glooory on 17/3/31.
 */
@FragmentScope
public class SearchResultModel extends BasePetalModel<ServiceManager, CacheManager>
        implements SearchResultContract.Model{

    @Inject
    public SearchResultModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }


}
