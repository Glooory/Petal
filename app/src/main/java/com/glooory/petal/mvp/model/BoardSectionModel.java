package com.glooory.petal.mvp.model;

import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.api.cache.CacheManager;
import com.glooory.petal.mvp.model.api.service.ServiceManager;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.PinListBean;
import com.glooory.petal.mvp.model.entity.PinSingleBean;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.jess.arms.di.scope.FragmentScope;

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
 * Created by Glooory on 17/3/28.
 */
@FragmentScope
public class BoardSectionModel extends BasePetalModel<ServiceManager, CacheManager>
        implements BoardContract.SectionModel {

    private int mMaxId;

    @Inject
    public BoardSectionModel(ServiceManager serviceManager, CacheManager cacheManager) {
        super(serviceManager, cacheManager);
    }

    @Override
    public boolean isMine(String userId) {
        return userId.equals(String.valueOf((int) SPUtils.get(Constants.PREF_USER_ID, 0)));
    }

    @Override
    public Observable<List<PinBean>> getBoardPins(String boardId) {
        return mServiceManager.getBoardService()
                .getBoardPins(boardId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getPinId();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PinBean>> getBoardPinsMore(String boardId) {
        return mServiceManager.getBoardService()
                .getBoardPinsMore(boardId, mMaxId, Constants.PER_PAGE_LIMIT)
                .retryWhen(new RetryWithDelay(1, 1))
                .map(new Func1<PinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(PinListBean pinListBean) {
                        if (pinListBean.getPins() != null && pinListBean.getPins().size() > 0) {
                            mMaxId = pinListBean.getPins().get(pinListBean.getPins().size() - 1).getPinId();
                        } else {
                            pinListBean.setPins(new ArrayList<PinBean>(0));
                        }
                        return pinListBean.getPins();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<PinBean> editPin(final String pinId, String boardId, String des) {
        return mServiceManager.getPinService()
                .editPin(pinId, boardId, des)
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
    public Observable<Void> deletePin(String pinId) {
        return mServiceManager.getPinService()
                .deletePin(pinId)
                .retryWhen(new RetryWithDelay(1, 1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
