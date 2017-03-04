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

        void setAdapter(HomePinsAdapter adapter);

        void showLoadingMore();
    }

    interface Model extends IModel{

        Observable<PinListBean> getLatestAllPins();

        Observable<PinListBean> getLatestAllPinsNext(int maxPinId);

        Observable<PinListBean> getLatestFollowingPins();

        Observable<PinListBean> getLatestFollowingPinsNext(int maxPinId);

        Observable<PinListBean> getLatestPopularPins();

        Observable<PinListBean> getLatestPopularPinsNext(int maxPinId);

    }
}
