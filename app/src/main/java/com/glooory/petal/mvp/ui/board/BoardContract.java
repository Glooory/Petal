package com.glooory.petal.mvp.ui.board;

import android.graphics.drawable.Drawable;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import rx.Observable;

/**
 * Created by Glooory on 17/3/27.
 */

public interface BoardContract {

    interface View extends BaseView {

        void hideBoardOperateBtn();

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
    }

    interface Model extends IModel {

        boolean isMine(String userId);

        Observable<BoardBean> getBoard(String boardId);
    }
}
