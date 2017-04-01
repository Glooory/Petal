package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.ui.category.CategoryContract;
import com.glooory.petal.mvp.ui.home.HomePinAdapter;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.BasePetalPresenter;
import rx.functions.Action0;

/**
 * Created by Glooory on 17/4/1.
 */
@FragmentScope
public class CategoryPresenter extends BasePetalPresenter<CategoryContract.View, CategoryContract.Model> {

    private BasePetalAdapter mAdapter;
    private String mCategory;

    @Inject
    public CategoryPresenter(CategoryContract.View rootView, CategoryContract.Model model) {
        super(rootView, model);
    }

    public void setAdapter(BasePetalAdapter adapter) {
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

    public void getCategoryPins(String category) {
        mCategory = category;
        mModel.getCategoryPins(mCategory)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        if (pinBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter();
                            return;
                        }
                        mAdapter.setNewData(pinBeen);
                    }
                });
    }

    public void getCategoryPinsMore() {
        mModel.getCategoryPinsMore(mCategory)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        if (pinBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter();
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        mAdapter.addData(pinBeen);
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void launchPinDetailActivity(Activity activity, View view, int position) {
        PinBean pinBean = ((HomePinAdapter) mAdapter).getItem(position);
        float aspectRatio = pinBean.getFile().getWidth() /
                (float) pinBean.getFile().getHeight();
        if (aspectRatio < 0.3) {
            aspectRatio = 0.3F;
        }
        PinDetailActivity.launch(activity,
                pinBean.getPinId(),
                aspectRatio,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin),
                DrawableUtils.getBasicColorStr(((HomePinAdapter) mAdapter).getItem(position)));
    }

    /**
     * Launch UserActivity, 仅当用户点击的是采集底部的用户头像时
     * @param activity
     * @param view
     * @param position
     */
    public void launchUserActivityFromPin(Activity activity, View view, int position) {
        PinBean pinBean = ((HomePinAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(pinBean.getUserId());
        UserActivity.launch(activity,
                userId,
                pinBean.getUser().getUsername(),
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin_avatar));
    }
}
