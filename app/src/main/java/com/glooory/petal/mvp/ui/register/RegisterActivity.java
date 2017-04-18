package com.glooory.petal.mvp.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.glooory.petal.R;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;

/**
 * Created by Glooory on 17/4/14.
 */

public class RegisterActivity extends BasePetalActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private RegisterFragment mRegisterFragment;
    private RegisterConfirmFragment mRegisterConfirmFragment;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegisterFragment = RegisterFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mRegisterFragment)
                .addToBackStack(RegisterFragment.class.getSimpleName())
                .commit();
    }

    @Override
    protected void initData() {

    }

    public void showRegisterConfirmFragment(String phone) {
        mRegisterConfirmFragment = RegisterConfirmFragment.newInstance(phone);
        getSupportFragmentManager().beginTransaction()
                .hide(mRegisterFragment)
                .add(R.id.container, mRegisterConfirmFragment)
                .addToBackStack(RegisterConfirmFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            handleBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 处理 back 点击事件
     */
    private void handleBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            finishConfirmFragment();
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finishRegisterFragment();
            finishSelf();
        }
    }

    private void finishConfirmFragment() {
        getSupportFragmentManager().beginTransaction()
                .remove(mRegisterConfirmFragment)
                .show(mRegisterFragment)
                .commit();
    }

    private void finishRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .remove(mRegisterFragment)
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            handleBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mRegisterConfirmFragment = null;
        mRegisterFragment = null;
        super.onDestroy();
    }
}
