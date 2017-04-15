package com.glooory.petal.mvp.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

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

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, RegisterFragment.newInstance())
                .addToBackStack(RegisterFragment.class.getName())
                .commit();
    }

    @Override
    protected void initData() {

    }

    public void showRegisterConfirmFragment(String phone) {
        // TODO: 17/4/14 Show RegisterConfirmFragment
    }
}
