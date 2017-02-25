package com.glooory.petal.mvp.ui.home;

import com.glooory.petal.mvp.model.entity.PinListBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import rx.Observable;

/**
 * Created by Glooory on 17/2/18.
 */

public interface HomeContract {

    interface View extends BaseView{


    }

    interface Model extends IModel{

        Observable<PinsListBean> getLatestAllPins(boolean update);

        Observable<PinsListBean> getLatestAllPinsNext(int maxPinId, boolean update);

        Observable<PinListBean> getLatestFollowingPins(boolean update);

        Observable<PinListBean> getLatestFollowingPinsNext(int maxPinId,boolean update);

        Observable<PinListBean> getLatestPopularPins(boolean update);

        Observable<PinListBean> getLatestPopularPinsNext(int maxPinId, boolean update);

    }
}
