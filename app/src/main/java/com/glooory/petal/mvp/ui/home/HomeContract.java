package com.glooory.petal.mvp.ui.home;

import com.glooory.petal.mvp.model.entity.BasicUserInfoBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Glooory on 17/2/18.
 */

public interface HomeContract {

    interface View extends BaseView{

        void setAdapter(HomePinAdapter adapter);

        void showLoadingMore();

        void showNoMoreDataFooter(boolean showAnyway);
    }

    interface Model extends IModel{

        Observable<List<PinBean>> getLatestAllPins();

        Observable<List<PinBean>> getLatestAllPinsNext(int maxPinId);

        Observable<List<PinBean>> getLatestFollowingPins();

        Observable<List<PinBean>> getLatestFollowingPinsNext(int maxPinId);

        Observable<List<PinBean>> getLatestPopularPins();

        Observable<List<PinBean>> getLatestPopularPinsNext(int maxPinId);

        Observable<BasicUserInfoBean> getMyselfBasicInfo();
    }
}
