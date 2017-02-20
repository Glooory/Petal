package com.glooory.petal.mvp.model.api.cache;

import com.jess.arms.http.BaseCacheManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Glooory on 17/2/20.
 */
@Singleton
public class CacheManager implements BaseCacheManager {

    private HomeCache mHomeCache;

    /**
     * 如果需要添加Cache只需在构造方法中添加对应的Cache,
     * 在提供get方法返回出去,只要在CacheModule提供了该Cache Dagger2会自行注入
     *
     * @param
     */
    @Inject
    public CacheManager(HomeCache homeCache) {
        mHomeCache = homeCache;
    }

    /**
     * 这里可以释放一些资源(注意这里是单例，即不需要在activity的生命周期调用)
     */
    @Override
    public void onDestory() {

    }

    public HomeCache getHomeCache() {
        return mHomeCache;
    }
}
