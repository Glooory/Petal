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
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import common.BasePetalPresenter;
import common.PetalApplication;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/27.
 */
@ActivityScope
public class BoardPresenter extends BasePetalPresenter<BoardContract.View, BoardContract.Model> {

    private String mBoardId;
    private boolean mIsMine;
    private boolean mIsFollowed;
    private String mBoardName;
    private String mBoardDes;
    private int mPinCount;
    private int mFollowerCount;
    private String mCategory;

    @Inject
    public BoardPresenter(BoardContract.View rootView, BoardContract.Model model) {
        super(rootView, model);
    }

    public void getBoardInfo(final String boardId) {
        mModel.getBoard(boardId)
                .compose(RxUtils.<BoardBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<BoardBean>() {
                    @Override
                    public void onNext(BoardBean boardBean) {
                        mPinCount = boardBean.getPinCount();
                        mFollowerCount = boardBean.getFollowCount();
                        mIsFollowed = boardBean.isFollowing();
                        mBoardName = boardBean.getTitle();
                        mBoardDes = boardBean.getDescription();
                        mCategory = boardBean.getCategoryId();
                        updateBoardInfo();
                    }
                });
    }

    public void updateBoardInfo(BoardBean boardBean, String userName) {
        mRootView.showBoardUserName(setBoardBelongStyle(userName));

        if (boardBean == null) {
            return;
        }

        mBoardId = String.valueOf(boardBean.getBoardId());
        mIsMine = mModel.isMine(String.valueOf(boardBean.getUserId()));
        mIsFollowed = boardBean.isFollowing();
        mPinCount = boardBean.getPinCount();
        mFollowerCount = boardBean.getFollowCount();
        mBoardDes = boardBean.getDescription();
        mBoardName = boardBean.getTitle();
        mCategory = boardBean.getCategoryId();

        mRootView.showViewPager();
        updateBoardInfo();

        loadBoardThumbnails(boardBean);
    }

    private void updateBoardInfo() {

        if (!TextUtils.isEmpty(mBoardName)) {
            mRootView.showBoardName(mBoardName);
        }

        if (!TextUtils.isEmpty(mBoardDes)) {
            mRootView.showBoardDescription(mBoardDes);
        } else {
            mRootView.hideBoardDescription();
        }

        Drawable actionDrawable;
        String action;
        if (mIsMine) {
            actionDrawable = ContextCompat.getDrawable(PetalApplication.getContext(),
                    R.drawable.ic_edit_white_24dp);
            action = PetalApplication.getContext().getString(R.string.edit);
        } else {
            if (mIsFollowed) {
                actionDrawable = ContextCompat.getDrawable(PetalApplication.getContext(),
                        R.drawable.ic_check_white_24dp);
                action = PetalApplication.getContext().getString(R.string.followed);
            } else {
                actionDrawable = ContextCompat.getDrawable(PetalApplication.getContext(),
                        R.drawable.ic_add_white_24dp);
                action = PetalApplication.getContext().getString(R.string.following);
            }
        }
        mRootView.showBoardOperateBtn(action, actionDrawable);

        String[] titles = new String[2];
        Resources resources = PetalApplication.getContext().getResources();
        titles[0] = String.format(resources.getString(R.string.format_collection_count),
                String.valueOf(mPinCount));
        titles[1] = String.format(resources.getString(R.string.format_following_count),
                mFollowerCount);
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
                FastBlurUtils.doBlur(bitmap, 10, false));
        mRootView.showAppBarBackground(blurDrawable);
    }

    public void onBoardEditBtnClick() {
        if (mIsMine) {
            showEditBoardDialog();
        } else {
            actionFollowBoard();
        }
    }

    private void showEditBoardDialog() {
        EditBoardDiglogFragment editBoardDiglogFragment = EditBoardDiglogFragment
                .newInstance(mBoardId, mBoardName, mBoardDes, mCategory);
        editBoardDiglogFragment.setBoardEditListener(new EditBoardDiglogFragment.OnBoardEditListener() {
            @Override
            public void onBoardEdit(String boardId, String boardName, String boardDes, String category) {
                commitBoardEditInfo(boardId, boardName, boardDes, category);
            }

            @Override
            public void onBoardDelete(String boardId) {
                mRootView.showDeleteBoardConfirmDialog(boardId);
            }
        });
        mRootView.showEditBoardDialog(editBoardDiglogFragment);
    }

    /**
     * 关注或者取消关注画板
     */
    private void actionFollowBoard() {
        mModel.followBoard(mBoardId, mIsFollowed)
                .delay(300, TimeUnit.MILLISECONDS)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showProcessingBar();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideProcessingBar();
                    }
                })
                .compose(RxUtils.<FollowBoardResultBean>bindToLifecycle(mRootView))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<FollowBoardResultBean>() {
                    @Override
                    public void onNext(FollowBoardResultBean followBoardResultBean) {
                        mFollowerCount = mIsFollowed ? --mFollowerCount : ++mFollowerCount;
                        mIsFollowed = !mIsFollowed;
                        updateBoardInfo();
                    }
                });
    }

    /**
     * 提交用户对画板的修改
     * @param boardId
     * @param boardName
     * @param boardDes
     * @param category
     */
    private void commitBoardEditInfo(String boardId, String boardName, String boardDes,
            String category) {
        mModel.editBoard(boardId, boardName, boardDes, category)
                .compose(RxUtils.<UserBoardSingleBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBoardSingleBean>() {
                    @Override
                    public void onNext(UserBoardSingleBean userBoardSingleBean) {
                        if (userBoardSingleBean.getBoard() != null) {
                            mBoardDes = userBoardSingleBean.getBoard().getDescription();
                            mBoardName = userBoardSingleBean.getBoard().getTitle();
                            mCategory = userBoardSingleBean.getBoard().getCategoryId();
                            updateBoardInfo();
                            mRootView.showMessage(PetalApplication.getContext()
                                    .getString(R.string.msg_edit_success));
                        }
                    }
                });
    }

    public void deleteBoard(String boardId) {
        mModel.deleteBoard(boardId)
                .compose(RxUtils.<UserBoardSingleBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBoardSingleBean>() {
                    @Override
                    public void onNext(UserBoardSingleBean userBoardSingleBean) {
                        Subscription s = Observable.just(1)
                                .delay(500, TimeUnit.MILLISECONDS)
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer integer) {
                                        mRootView.showDeleteBoardSuccess();
                                    }
                                });
                        addSubscrebe(s);
                    }
                });
    }

    private SpannableString setBoardBelongStyle(String boardUserName) {
        SpannableString spannableString = new SpannableString(
                PetalApplication.getContext().getString(R.string.msg_board_belongs_to)
                        + Constants.SPACE + boardUserName);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(PetalApplication.getContext(),
                R.color.grey_300)), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(0.9F), 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public boolean isMine() {
        return mIsMine;
    }

    public int getPinCount() {
        return mPinCount;
    }

    public int getFollowerCount() {
        return mFollowerCount;
    }
}
