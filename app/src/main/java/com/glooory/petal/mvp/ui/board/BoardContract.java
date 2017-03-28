package com.glooory.petal.mvp.ui.board;

import android.graphics.drawable.Drawable;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Glooory on 17/3/27.
 */

public interface BoardContract {

    interface View extends BaseView {

        void showBoardOperateBtn(String operate, Drawable actionDrawable);

        void showBoardName(CharSequence boardName);

        void showBoardUserName(CharSequence userName);

        void showBoardDescription(String des);

        void hideBoardDescription();

        void showBoardThumbnailFirst(String imgUrlKey);

        void showBoardThumbnailSecond(String imgUrlKey);

        void showBoardThumbnailThird(String imgUrlKey);

        void showBoardThumbnailFourth(String imgUrlKey);

        void showAppBarBackground(Drawable drawable);

        void showTabTitles(String[] titles);

        void showViewPager();

        void showEditBoardDialog(EditBoardDiglogFragment editBoardDiglogFragment);

        void showDeleteBoardSuccess();

        void showDeleteBoardConfirmDialog(String boardId);

        void showProcessingBar();

        void hideProcessingBar();
    }

    interface Model extends IModel {

        boolean isMine(String userId);

        Observable<BoardBean> getBoard(String boardId);

        Observable<FollowBoardResultBean> followBoard(String boardId, boolean isFollowed);

        Observable<UserBoardSingleBean> editBoard(String boardId, String boardName, String des, String category);

        Observable<UserBoardSingleBean> deleteBoard(String boardId);
    }

    interface SectionView extends BaseView {

        void showLoadingMore();

        void showNoMoreDataFooter(boolean showAnyway);

        void showDeletePinConfirmDialog(String pinId, int position);

        void showEditPinDialog(EditPinDialogFragment editPinDialogFragment);

        void showPinDeleted();
    }

    interface SectionModel extends IModel {

        boolean isMine(String userId);

        Observable<List<PinBean>> getBoardPins(String boardId);

        Observable<List<PinBean>> getBoardPinsMore(String boardId);

        Observable<PinBean> editPin(String pinId, String boardId, String des);

        Observable<Void> deletePin(String pinId);
    }
}
