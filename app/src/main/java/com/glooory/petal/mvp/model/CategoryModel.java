package com.glooory.petal.mvp.model;

import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.ui.category.CategoryContract;

import common.BasePetalModel;

/**
 * Created by Glooory on 17/4/1.
 */

public class CategoryModel extends BasePetalModel<ServiceManager, CacheManager>
        implements CategoryContract.Model {

    private int mMaxId;

    public CategoryModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }


}
