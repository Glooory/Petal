package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.ui.home.HomeContract;
import com.jess.arms.di.scope.FragmentScope;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    public Observable<List<PinBean>> getLatestAllPins() {
        return mServiceManager.getHomeService()
                .getLatestAllPins(Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getLatestAllPinsNext(int maxPinId) {
        return mServiceManager.getHomeService()
                .getLatestAllPinsNext(maxPinId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getLatestFollowingPins() {
        return mServiceManager.getHomeService()
                .getLatestFollowingPins(Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getLatestFollowingPinsNext(int maxPinId) {
        return mServiceManager.getHomeService()
                .getLatestFollowingPinsNext(maxPinId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getLatestPopularPins() {
        return mServiceManager.getHomeService()
                .getLatestPopularPins(Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getLatestPopularPinsNext(int maxPinId) {
        return mServiceManager.getHomeService()
                .getLatestPopularPinsNext(maxPinId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        return pinListBean.getPins();
                    }
                })
                .filter(new Func1<List<PinBean>, Boolean>() {
                    @Override
                    public Boolean call(List<PinBean> pinBeen) {
                        return pinBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
