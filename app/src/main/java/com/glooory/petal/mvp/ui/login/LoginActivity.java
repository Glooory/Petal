package com.glooory.petal.mvp.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.glooory.petal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.PEActivity;

/**
 * Created by Glooory on 17/3/4.
 */

public class LoginActivity extends PEActivity {

    @BindView(R.id.button_skip_login)
    Button mBtnSkipLogin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.textview_user_account)
    AutoCompleteTextView mTextViewUserAccount;
    @BindView(R.id.edittext_user_password)
    EditText mEdittextUserPassword;
    @BindView(R.id.button_login)
    Button mButtonLogin;

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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
