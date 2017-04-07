package com.glooory.petal.mvp.model;

import android.app.Activity;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.service.DownloadPinService;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.PinSingleBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.LikeResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.PinDetailBean;
import com.glooory.petal.mvp.ui.pindetail.PinDetailContract;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import common.BasePetalModel;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/17.
 */
@ActivityScope
public class PinDetailModel extends BasePetalModel<ServiceManager, CacheManager>
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
        return mServiceManager.getPinService()
                .getPinDetailInfo(mPinId)
                .retryWhen(new RetryWithDelay(2, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectionInfoBean> isCollected() {
        return mServiceManager.getPinService()
                .isCollected(mPinId, true)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getRecommendedPins(int page) {
        return mServiceManager.getPinService()
                .getRecommendedPins(mPinId, page, PAGE_SIZE)
                .retryWhen(new RetryWithDelay(2, 2))
                .map(new Func1<List<PinBean>, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(List<PinBean> pinBeen) {
                        if (pinBeen.size() == 0) {
                            pinBeen = new ArrayList<PinBean>(0);
                        }
                        return pinBeen;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectResultBean> collectPin(String boardId, String des) {
        return mServiceManager.getPinService()
                .collectPin(boardId, des, String.valueOf(mPinId))
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PinBean> editPin(String boardId, String des) {
        return mServiceManager.getPinService()
                .editPin(String.valueOf(mPinId), boardId, des)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinSingleBean, PinBean>() {
                    @Override
                    public PinBean call(PinSingleBean pinSingleBean) {
                        return pinSingleBean.getPin();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Void> deletePin() {
        return mServiceManager.getPinService()
                .deletePin(String.valueOf(mPinId))
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<LikeResultBean> likePin(boolean isLiked) {
        return mServiceManager.getPinService()
                .likePin(mPinId, isLiked ? Constants.HTTP_ARGS_LIKE : Constants.HTTP_ARGS_UNLIKE)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void downloadPin(Activity activity, String pinKey, String pinType) {
        DownloadPinService.launch(activity, pinKey, pinType);
    }
}
