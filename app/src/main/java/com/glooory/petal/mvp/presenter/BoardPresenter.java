package com.glooory.petal.mvp.presenter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.FastBlurUtils;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import common.BasePetalPresenter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/3/27.
 */
@ActivityScope
public class BoardPresenter extends BasePetalPresenter<BoardContract.View, BoardContract.Model> {

    private boolean isMine;
    private boolean isFollowed;

    @Inject
    public BoardPresenter(BoardContract.View rootView, BoardContract.Model model) {
        super(rootView, model);
    }

    public void getBoardInfo(String boardId) {
        mModel.getBoard(boardId)
                .compose(RxUtils.<BoardBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<BoardBean>() {
                    @Override
                    public void onNext(BoardBean boardBean) {
                        updateTabTitles(boardBean);
                    }
                });
    }

    public void updateBoardInfo(BoardBean boardBean, String userName) {
        mRootView.showBoardUserName(setBoardBelongStyle(userName));

        if (boardBean == null) {
            return;
        }

        isMine = mModel.isMine(String.valueOf(boardBean.getUserId()));
        isFollowed = boardBean.isFollowing();

        updateTabTitles(boardBean);

        if (isMine) {
            mRootView.hideBoardOperateBtn();
        } else {
            Drawable actionDrawable;
            String action;
            if (isFollowed) {
                actionDrawable = ContextCompat.getDrawable(PetalApplication.getContext(),
                        R.drawable.ic_check_white_24dp);
                action = PetalApplication.getContext().getString(R.string.followed);
            } else {
                actionDrawable = ContextCompat.getDrawable(PetalApplication.getContext(),
                        R.drawable.ic_add_white_24dp);
                action = PetalApplication.getContext().getString(R.string.following);
            }
            mRootView.showBoardOperateBtn(action, actionDrawable);
        }

        if (!TextUtils.isEmpty(boardBean.getTitle())) {
            mRootView.showBoardName(boardBean.getTitle());
        }

        if (!TextUtils.isEmpty(boardBean.getDescription())) {
            mRootView.showBoardDescription(boardBean.getDescription());
        } else {
            mRootView.hideBoardDescription();
        }

        loadBoardThumbnails(boardBean);
    }

    private void updateTabTitles(BoardBean boardBean) {
        if (boardBean == null) {
            return;
        }

        String[] titles = new String[2];
        Resources resources = PetalApplication.getContext().getResources();
        titles[0] = String.format(resources.getString(R.string.format_collection_count),
                String.valueOf(boardBean.getPinCount()));
        titles[1] = String.format(resources.getString(R.string.format_following_count),
                boardBean.getFollowCount());
        mRootView.showTabTitles(titles);
    }

    private void loadBoardThumbnails(BoardBean boardBean) {
        if (boardBean == null || boardBean.getPins() == null) {
            return;
        }

        List<PinBean> pinList = boardBean.getPins();
        if (pinList.size() > 0) {
            mRootView.showBoardThumbnailFirst(pinList.get(0).getFile().getKey());
        }

        if (pinList.size() > 1) {
            mRootView.showBoardThumbnailSecond(pinList.get(1).getFile().getKey());
        }

        if (pinList.size() > 2) {
            mRootView.showBoardThumbnailThird(pinList.get(2).getFile().getKey());
        }

        if (pinList.size() > 3) {
            mRootView.showBoardThumbnailFourth(pinList.get(3).getFile().getKey());
        }
    }

    public void processBlurBackgroundImg(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }

        Drawable blurDrawable = new BitmapDrawable(PetalApplication.getContext().getResources(),
                FastBlurUtils.doBlur(bitmap, 15, false));
        mRootView.showAppBarBackground(blurDrawable);
    }

    private SpannableString setBoardBelongStyle(String boardUserName) {
        SpannableString spannableString = new SpannableString(
                PetalApplication.getContext().getString(R.string.msg_board_belongs_to)
                        + Constants.SPACE + boardUserName);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(PetalApplication.getContext(),
                R.color.secondary_text)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.9F), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}
