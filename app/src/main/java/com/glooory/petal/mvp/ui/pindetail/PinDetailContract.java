package com.glooory.petal.mvp.ui.pindetail;

import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.pindetail.CollectionInfoBean;
import com.glooory.petal.mvp.model.entity.pindetail.LikeResultBean;
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

        void showBoardImgFirst(String imageUrlKey);

        void showBoardImgSecond(String imageUrlKey);

        void showBoardImgThird(String imageUrlKey);

        void showBoardImgFourth(String imageUrlKey);

        void showCollectSbtnChecked(boolean checked, boolean isPerformClick);

        void showLikeSbtnChecked(boolean checked, boolean isPerformClick);

        void hideLikeSbtn();

        void showNoMoreDataFooter(boolean show);

        void showCollectDialog(CollectDialogFragment collectDialogFragment);

        void showEditDialog(EditPinDialogFragment editPinDialogFragment);

        void showDeleteConfirmDialog();

        void killMyself();
    }

    interface Model extends IModel {

        Observable<PinDetailBean> getPinDetailInfo(int pinId);

        Observable<CollectionInfoBean> isCollected();

        Observable<List<PinBean>> getRecommendedPins(int page);

        Observable<CollectResultBean> collectPin(String boardId, String des);

        Observable<PinBean> editPin(String boardId, String des);

        Observable<Void> deletePin();

        Observable<LikeResultBean> likePin(boolean isLiked);
    }
}

