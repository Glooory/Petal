package com.glooory.petal.mvp.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.user.UserBoardSingleBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.glooory.petal.mvp.ui.user.board.EditBoardDiglogFragment;
import com.glooory.petal.mvp.ui.user.board.UserBoardAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import common.PEAdapter;
import common.PEApplication;
import common.PEPresenter;

/**
 * Created by Glooory on 17/3/21.
 */
@FragmentScope
public class UserSectionPresenter extends PEPresenter<UserContract.SectionView, UserContract.SectionModel> {

    private PEAdapter mAdapter;
    private String mUserId;
    private boolean mIsMe;

    @Inject
    public UserSectionPresenter(UserContract.SectionView rootView, UserContract.SectionModel model) {
        super(rootView, model);
    }

    public boolean isMe(String userId) {
        mIsMe = mModel.isMe(userId);
        return mIsMe;
    }

    public void setAdapter(PEAdapter adapter) {
        mAdapter = adapter;
        initAdapter();
    }

    private void initAdapter() {
        mAdapter.setLoadMoreView(new WindmillLoadMoreFooter());
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRootView.showLoadingMore();
            }
        });
    }

    public void getBoards(String userId) {
        mUserId = userId;
        mModel.getBoards(mUserId)
                .compose(RxUtils.<List<BoardBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        mAdapter.setNewData(boardBeanList);
                        mRootView.showNoMoreDataFooter();
                    }
                });
    }

    public void getBoardsMore() {
        mModel.getBoardsMore(mUserId)
                .compose(RxUtils.<List<BoardBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        mAdapter.addData(boardBeanList);
                        mRootView.showNoMoreDataFooter();
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void operateBtnClicked(int position) {
        if (!mModel.isLogin()) {
            mRootView.showLoginNav();
            return;
        }
        if (mIsMe) {
            showEditBoardDialog(position);
        } else {
            actionFollowBoard(position);
        }
    }

    private void showEditBoardDialog(final int position) {
        final BoardBean boardBean = ((UserBoardAdapter) mAdapter).getItem(position);
        EditBoardDiglogFragment fragment = EditBoardDiglogFragment
                .newInstance(String.valueOf(boardBean.getBoardId()),
                        boardBean.getTitle(), boardBean.getDescription(), boardBean.getCategoryId());
        fragment.setBoardEditListener(new EditBoardDiglogFragment.OnBoardEditListener() {
            @Override
            public void onBoardEdit(String boardId, String boardName, String boardDes, String category) {
                commitBoardEditInfo(boardId, boardName, boardDes, category, position);
            }

            @Override
            public void onBoardDelete(String boardId) {
                mRootView.showDeleteBoardConfirmDialog(boardId, position);
            }
        });
        mRootView.showEditBoardDialog(fragment);
    }

    private void actionFollowBoard(final int position) {
        final BoardBean boardBean = ((UserBoardAdapter) mAdapter).getItem(position);
        String boardId = String.valueOf(boardBean.getBoardId());
        boolean isFollowed = boardBean.isFollowing();
        mModel.followBoard(boardId, isFollowed)
                .compose(RxUtils.<FollowBoardResultBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<FollowBoardResultBean>() {
                    @Override
                    public void onNext(FollowBoardResultBean followBoardResultBean) {
                        boolean isFollowedTemp = !boardBean.isFollowing();
                        boardBean.setFollowing(isFollowedTemp);
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }

    private void commitBoardEditInfo(String boardId, String boardName, String boardDes,
            String category, final int position) {
        mModel.editBoard(boardId, boardName, boardDes, category)
                .compose(RxUtils.<UserBoardSingleBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBoardSingleBean>() {
                    @Override
                    public void onNext(UserBoardSingleBean userBoardSingleBean) {
                        if (userBoardSingleBean.getBoard() != null) {
                            ((UserBoardAdapter) mAdapter).getItem(position).
                                    setTitle(userBoardSingleBean.getBoard().getTitle());
                            ((UserBoardAdapter) mAdapter).getItem(position)
                                    .setDescription(userBoardSingleBean.getBoard().getDescription());
                            ((UserBoardAdapter) mAdapter).getItem(position)
                                    .setCategoryId(userBoardSingleBean.getBoard().getCategoryId());
                            Logger.d(userBoardSingleBean.getBoard().getCategoryId());
                            Logger.d(((UserBoardAdapter) mAdapter).getItem(position).getCategoryId());
                            mAdapter.notifyItemChanged(position);
                            mRootView.showMessage(PEApplication.getContext()
                                    .getString(R.string.msg_edit_success));
                        }
                    }
                });
    }

    public void deleteBoard(String boardId, final int position) {
        mModel.deleteBoard(boardId)
                .compose(RxUtils.<UserBoardSingleBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<UserBoardSingleBean>() {
                    @Override
                    public void onNext(UserBoardSingleBean userBoardSingleBean) {
                        if (userBoardSingleBean.getBoard() != null) {
                            mRootView.showMessage(PEApplication.getContext()
                                    .getString(R.string.msg_delete_board_success));
                            mAdapter.getData().remove(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
