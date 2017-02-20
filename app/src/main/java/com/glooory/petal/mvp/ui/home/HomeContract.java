package com.glooory.petal.mvp.ui.home;

import com.glooory.petal.mvp.model.entity.ListPinsBean;
import com.glooory.petal.mvp.model.entity.PinsListBean;
import com.jess.arms.mvp.IModel;

import rx.Observable;

/**
 * Created by Glooory on 17/2/18.
 */

public interface HomeContract {


    interface Model extends IModel{

        Observable<PinsListBean> getLatestAllPins(boolean update);

        Observable<PinsListBean> getLatestAllPinsNext(int maxPinId, boolean update);

        Observable<ListPinsBean> getLatestFollowingPins(boolean update);

        Observable<ListPinsBean> getLatestFollowingPinsNext(int maxPinId,boolean update);

        Observable<ListPinsBean> getLatestPopularPins(boolean update);

        Observable<ListPinsBean> getLatestPopularPinsNext(int maxPinId, boolean update);

    }
}
