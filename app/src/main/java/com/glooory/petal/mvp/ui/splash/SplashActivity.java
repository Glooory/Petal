package com.glooory.petal.mvp.ui.splash;

import android.view.KeyEvent;

import common.AppComponent;
import common.PEActivity;

/**
 * Created by Glooory on 17/3/4.
 */

public class SplashActivity extends PEActivity {



    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //屏蔽返回键点击事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
