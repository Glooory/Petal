package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.BaseClientInfo;
import com.glooory.petal.app.util.SPUtils;
import com.jess.arms.http.BaseCacheManager;
import com.jess.arms.http.BaseServiceManager;
import com.jess.arms.mvp.IModel;

/**
 * Created by Glooory on 17/2/20.
 */

public class BasePEModel<S extends BaseServiceManager, C extends BaseCacheManager>
        implements IModel {

    protected S mServiceManager;//服务管理类,用于网络请求
    protected C mCacheManager;//缓存管理类,用于管理本地或者内存缓存

    public BasePEModel(S serviceManager, C cacheManager) {
        this.mServiceManager = serviceManager;
        this.mCacheManager = cacheManager;
    }

    @Override
    public void onDestory() {
        if (mServiceManager != null) {
            mServiceManager = null;
        }
        if (mCacheManager != null) {
            mCacheManager = null;
        }
    }

    public String getAuthorization() {
        boolean isLogin = (boolean) SPUtils.get(Constants.PREF_IS_LOGIN, false);
        if (isLogin) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(SPUtils.get(Constants.PREF_TOKEN_TYPE, " "))
                    .append(" ")
                    .append(SPUtils.get(Constants.PREF_TOKEN_ACCESS, " "));
            return stringBuilder.toString();
        }
        return BaseClientInfo.CLIENT_INFO_DEFAULT;
    }
}
