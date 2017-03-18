package com.glooory.petal.mvp.ui.pindetail;

import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.PinDetailBean;
import com.glooory.petal.mvp.ui.home.HomePinsAdapter;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Glooory on 17/3/17.
 */

public interface PinDetailContract {

    interface View extends BaseView {

        void setAdapter(HomePinsAdapter adapter);

        void showLoadingMore();

        void showCollectCount(String collectCount);

        void showLikeCount(String likeCount);

        void showCollectDes(String des);

        void hideCollectDes();

        void showUserName(String userName);

        void showCollectTime(String collectTime);

        void showBoardName(String boardName);

        void showPinImage(String imageUrlKey);

        void showAvatarImage(String avatarKey);

        void showBoardImage(String imageurlKey);

        void showCollectSbtnChecked(boolean checked);

        void showLikeSbtnChecked(boolean checked);

        void hideLikeSbtn();

        void showNoMoreDataFooter(boolean show);
    }

    interface Model extends IModel {

        Observable<PinDetailBean> getPinDetailInfo(int pinId);

        Observable<CollectionInfoBean> isCollected();

        Observable<List<PinBean>> getRecommendedPins(int page);

        Observable<List<PinBean>> getMoreRecommendedPins(int page);
    }
}

