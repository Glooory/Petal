package com.glooory.petal.mvp.ui.login;

import android.app.Activity;
import android.content.Intent;

import com.glooory.petal.R;

import common.AppComponent;
import common.PEActivity;

/**
 * Created by Glooory on 17/3/4.
 */

public class LoginActivity extends PEActivity {


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
