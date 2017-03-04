package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.ui.home.HomeContract;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Glooory on 17/2/21.
 */
@FragmentScope
public class HomeModel extends BasePEModel<ServiceManager, CacheManager>
        implements HomeContract.Model {

    @Inject
    public HomeModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<PinListBean> getLatestAllPins() {
        return mServiceManager.getHomeService()
                .getLatestAllPins(Constants.PER_PAGE_LIMIT);
//        return mCacheManager.getHomeCache()
//                .getLatestAllPins(observablePinList, new EvictDynamicKey(update))
//                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
//                    @Override
//                    public Observable<PinListBean> call(Reply<PinListBean> pinsListBeanReply) {
//                        return Observable.just(pinsListBeanReply.getData());
//                    }
//                });
    }

    @Override
    public Observable<PinListBean> getLatestAllPinsNext(int maxPinId) {
        return mServiceManager.getHomeService()
                .getLatestAllPinsNext(maxPinId, Constants.PER_PAGE_LIMIT);
//        return mCacheManager.getHomeCache()
//                .getLatestAllPinsNext(observablePinList,
//                        new DynamicKey(maxPinId),
//                        new EvictDynamicKey(update))
//                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
//                    @Override
//                    public Observable<PinListBean> call(Reply<PinListBean> pinsListBeanReply) {
//                        return Observable.just(pinsListBeanReply.getData());
//                    }
//                });
    }

    @Override
    public Observable<PinListBean> getLatestFollowingPins() {
        return mServiceManager.getHomeService()
                .getLatestFollowingPins(Constants.PER_PAGE_LIMIT);
//        return mCacheManager.getHomeCache()
//                .getLatestFollowingPins(observablePinList, new EvictDynamicKey(update))
//                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
//                    @Override
//                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
//                        return Observable.just(listPinsBeanReply.getData());
//                    }
//                });
    }

    @Override
    public Observable<PinListBean> getLatestFollowingPinsNext(int maxPinId) {
        return mServiceManager.getHomeService()
                .getLatestFollowingPinsNext(maxPinId, Constants.PER_PAGE_LIMIT);
//        return mCacheManager.getHomeCache()
//                .getLatestFollowingPinsNext(observablePinList,
//                        new DynamicKey(maxPinId),
//                        new EvictDynamicKey(update))
//                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
//                    @Override
//                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
//                        return Observable.just(listPinsBeanReply.getData());
//                    }
//                });
    }

    @Override
    public Observable<PinListBean> getLatestPopularPins() {
        return mServiceManager.getHomeService()
                .getLatestPopularPins(Constants.PER_PAGE_LIMIT);
//        return mCacheManager.getHomeCache()
//                .getLatestPopularPins(observablePinList,
//                        new EvictDynamicKey(update))
//                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
//                    @Override
//                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
//                        return Observable.just(listPinsBeanReply.getData());
//                    }
//                });
    }

    @Override
    public Observable<PinListBean> getLatestPopularPinsNext(int maxPinId) {
        return mServiceManager.getHomeService()
                .getLatestPopularPinsNext(maxPinId, Constants.PER_PAGE_LIMIT);
//        return mCacheManager.getHomeCache()
//                .getLatestPopularPinsNext(observablePinList,
//                        new DynamicKey(maxPinId),
//                        new EvictDynamicKey(update))
//                .flatMap(new Func1<Reply<PinListBean>, Observable<PinListBean>>() {
//                    @Override
//                    public Observable<PinListBean> call(Reply<PinListBean> listPinsBeanReply) {
//                        return Observable.just(listPinsBeanReply.getData());
//                    }
//                });
    }
}
