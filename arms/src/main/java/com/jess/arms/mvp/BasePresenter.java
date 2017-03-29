package com.jess.arms.mvp;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jess on 16/4/28.
 */
public class BasePresenter<V extends BaseView, M extends IModel> implements Presenter {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeSubscription mCompositeSubscription;

    protected V mRootView;
    protected M mModel;


    public BasePresenter(V rootView, M model) {
        this.mRootView = rootView;
        this.mModel = model;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        unSubscribe();//解除订阅
        if (mModel != null) {
            mModel.onDestory();
            this.mModel = null;
        }
        this.mRootView = null;
        this.mCompositeSubscription = null;
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);//将所有subscription放入,集中处理
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
        }
    }

    @Override
    public void unSubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();//保证activity结束时取消所有正在执行的订阅
        }
    }

}
