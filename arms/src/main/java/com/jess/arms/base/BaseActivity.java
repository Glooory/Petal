package com.jess.arms.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jess.arms.mvp.Presenter;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends Presenter> extends RxAppCompatActivity {

    public static final String IS_NOT_ADD_ACTIVITY_LIST =
            "is_add_activity_list";//是否加入到activity的list，管理

    protected final String TAG = this.getClass().getSimpleName();
    protected BaseApplication mApplication;
    private Unbinder mUnbinder;
    @Inject
    protected P mPresenter;

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.getAppManager().setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mApplication.getAppManager().getCurrentActivity() == this) {
            mApplication.getAppManager().setCurrentActivity(null);
        }
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        //如果intent包含了此字段,并且为true说明不加入到list
        // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
        boolean isNotAdd = false;
        if (getIntent() != null) {
            isNotAdd = getIntent().getBooleanExtra(IS_NOT_ADD_ACTIVITY_LIST, false);
        }

        if (!isNotAdd) {
            mApplication.getAppManager().addActivity(this);
        }

        setContentView(getLayoutId());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        componentInject();//依赖注入
        initView();
        initData();
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void componentInject();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApplication.getAppManager().removeActivity(this);
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        this.mPresenter = null;
        this.mUnbinder = null;
        this.mApplication = null;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

}
