package com.glooory.petal.mvp.presenter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.ui.user.UserContract;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import common.PEAdapter;
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
}
