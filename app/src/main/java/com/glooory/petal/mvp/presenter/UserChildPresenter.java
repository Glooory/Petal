package com.glooory.petal.mvp.presenter;

import com.glooory.petal.app.rx.BaseSubscriber;
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
public class UserChildPresenter extends PEPresenter<UserContract.ChildView, UserContract.Model> {

    private PEAdapter mAdapter;
    private String mUserId;

    @Inject
    public UserChildPresenter(UserContract.ChildView rootView, UserContract.Model model) {
        super(rootView, model);
    }

    public boolean isMe(String userId) {
        return mModel.isMe(userId);
    }

    public void setAdapter(PEAdapter adapter) {
        mAdapter = adapter;
    }

    public void getBoards(String userId) {
        mUserId = userId;
        mModel.getBoards(mUserId)
                .compose(RxUtils.<List<BoardBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        mAdapter.setNewData(boardBeanList);
                    }
                });
    }
}
