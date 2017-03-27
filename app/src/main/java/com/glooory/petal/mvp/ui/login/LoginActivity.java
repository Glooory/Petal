package com.glooory.petal.mvp.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.glooory.petal.R;
import com.glooory.petal.app.adapter.NoFilterringAdapter;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.di.component.DaggerLoginComponent;
import com.glooory.petal.di.module.LoginModule;
import com.glooory.petal.mvp.presenter.LoginPresenter;
import com.glooory.petal.mvp.ui.home.HomeActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Glooory on 17/3/4.
 */

public class LoginActivity extends BasePetalActivity<LoginPresenter> implements LoginContract.View {

    private static final String BUNDLE_IS_FROM_SPLASH = "is_from_splash";

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

    private ProgressDialog mProgressDialog;
    private boolean mIsFromSplashActivity = false;

    public static void launch(Activity activity, boolean isFromSplashActivity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(BUNDLE_IS_FROM_SPLASH, isFromSplashActivity);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
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

        RxView.clicks(mBtnSkipLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.saveSkipLoginTrue();
                        HomeActivity.launch(LoginActivity.this);
                        finishSelf();
                    }
                });

        RxTextView.editorActions(mEdittextUserPassword, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_DONE;
            }
        }).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        hideSoftwareKeyboard();
                        mPresenter.login(mTextViewUserAccount.getText().toString(),
                                mEdittextUserPassword.getText().toString());
                    }
                });

        RxView.clicks(mButtonLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        hideSoftwareKeyboard();
                        mPresenter.login(mTextViewUserAccount.getText().toString(),
                                mEdittextUserPassword.getText().toString());
                    }
                });
    }

    @Override
    protected void initData() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.msg_logining));
        mPresenter.loadHistoryAccount(this);
        mIsFromSplashActivity = getIntent().getBooleanExtra(BUNDLE_IS_FROM_SPLASH, false);
        if (!mIsFromSplashActivity) {
            mBtnSkipLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (mProgressDialog != null) {
                            mProgressDialog.show();
                        }
                    }
                });
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        if (mIsFromSplashActivity) {
            HomeActivity.launch(LoginActivity.this);
            finishSelf();
        } else {
            finishSelf();
        }
    }

    @Override
    public void setAdapter(NoFilterringAdapter adapter) {
        mTextViewUserAccount.setAdapter(adapter);
        mTextViewUserAccount.post(new Runnable() {
            @Override
            public void run() {
                mTextViewUserAccount.showDropDown();
            }
        });
    }

    @Override
    public void clearErrorInfo() {
        mTextViewUserAccount.setError(null);
        mEdittextUserPassword.setError(null);
    }

    @Override
    public void showAccountError(String error) {
        mTextViewUserAccount.requestFocus();
        mTextViewUserAccount.setError(error);
    }

    @Override
    public void showPasswordError(String error, boolean isAccountIllegal) {
        if (!isAccountIllegal) {
            mEdittextUserPassword.requestFocus();
        }
        mEdittextUserPassword.setError(error);
    }

    @Override
    public void showLoginFailed() {
        SnackbarUtil.showLong(LoginActivity.this, getString(R.string.msg_login_failed));
    }

    private void hideSoftwareKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEdittextUserPassword.getWindowToken(), 0);
    }

    @Override
    public void showLoginSuccess() {
        HomeActivity.launch(LoginActivity.this);
        finishSelf();
    }

    @Override
    public void onBackPressed() {
        killMyself();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            killMyself();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
