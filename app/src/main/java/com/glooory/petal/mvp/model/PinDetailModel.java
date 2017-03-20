package com.glooory.petal.mvp.model;

import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.PinDetailBean;
import com.glooory.petal.mvp.ui.pindetail.PinDetailContract;
import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/17.
 */
@ActivityScope
public class PinDetailModel extends BasePEModel<ServiceManager, CacheManager>
        implements PinDetailContract.Model {

    private static final int PAGE_SIZE = 10;
    private int mPinId;

    @Inject
    public PinDetailModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public Observable<PinDetailBean> getPinDetailInfo(int pinId) {
        mPinId = pinId;
        return mServiceManager.getPinBoardService()
                .getPinDetailInfo(mPinId)
                .retryWhen(new RetryWithDelay(2, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectionInfoBean> isCollected() {
        return mServiceManager.getOperateService()
                .isCollected(mPinId, true)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getRecommendedPins(int page) {
        return mServiceManager.getPinBoardService()
                .getRecommendedPins(mPinId, page, PAGE_SIZE)
                .retryWhen(new RetryWithDelay(2, 2))
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
    public Observable<CollectResultBean> collectPin(String boardId, String des) {
        return mServiceManager.getOperateService()
                .collectPin(boardId, des, String.valueOf(mPinId))
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
