package com.glooory.petal.mvp.ui.collect;

import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.collect.UploadResultBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import rx.Observable;

/**
 * Created by Glooory on 17/4/3.
 */

public interface CollectContract {

    interface View extends BaseView {

        void showUploadingProgressbar();

        void hideUploadingProgressbar();

        void showBoardSpinner(String[] boardNames, int lastEditedPosition);

        void showCollectButton(boolean enabled);

        void showEmptyPreview();

        void showNoneBoardPrompt();
    }

    interface Model extends IModel {

        Observable<LatestEditBoardsBean> getUserLatestBoards();

        Observable<UploadResultBean> uploadImage(String imagePath);

        Observable<CollectResultBean> collect(String boardId, String des, String field);

        void saveLatestEditedBoard(String boardName);

        int getBoardCount();
    }
}
