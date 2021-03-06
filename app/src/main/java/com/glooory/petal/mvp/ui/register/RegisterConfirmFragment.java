package com.glooory.petal.mvp.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.glooory.petal.R;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.di.component.DaggerRegisterComponent;
import com.glooory.petal.di.module.RegisterModule;
import com.glooory.petal.mvp.presenter.RegisterPresenter;
import com.glooory.petal.mvp.ui.home.HomeActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Glooory on 17/4/17.
 */

public class RegisterConfirmFragment extends BasePetalFragment<RegisterPresenter>
        implements RegisterContract.View{

    private static final String ARGS_PHONE_NUMBER = "phone";
    public static final int PARAMETER_ERROR_CAPTCHA = 0;
    public static final int PARAMETER_ERROR_USER_NAME = 1;
    public static final int PARAMETER_ERROR_PASSWORD = 2;
    public static final int PARAMETER_ERROR_DIFFERENT_PASSWORD = 3;
    public static final int PARAMETER_ERROR_PHONE_NUMBER = 4;

    @BindView(R.id.text_view_register_phone)
    TextView mTextViewPhone;
    @BindView(R.id.edit_text_register_captcha)
    EditText mEditTextCaptcha;
    @BindView(R.id.text_view_register_send_again)
    TextView mTextViewSendAgain;
    @BindView(R.id.edit_text_register_password)
    EditText mEditTextPassword;
    @BindView(R.id.edit_text_register_password_confirm)
    EditText mEditTextPasswordConfirm;
    @BindView(R.id.edit_text_register_user_name)
    EditText mEditTextUserName;
    @BindView(R.id.button_register)
    Button mButtonRegister;

    private String mPhoneNumber;

    public static RegisterConfirmFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(ARGS_PHONE_NUMBER, phone);
        RegisterConfirmFragment fragment = new RegisterConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhoneNumber = getArguments().getString(ARGS_PHONE_NUMBER);
    }

    @Override
    protected View initView(ViewGroup container) {
        mRootView = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_register_confirm, container, false);
        return mRootView;
    }

    @Override
    protected void setupViews() {
        mTextViewPhone.setText(mPhoneNumber);
        mTextViewSendAgain.setEnabled(false);

        RxView.clicks(mTextViewSendAgain)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.sendIdentityCodeAgain(mPhoneNumber);
                        mTextViewSendAgain.setEnabled(false);
                    }
                });

        RxTextView.editorActions(mEditTextPasswordConfirm, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_DONE;
            }
        }).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        handleRegister();
                    }
                });

        RxView.clicks(mButtonRegister)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        handleRegister();
                    }
                });

        mPresenter.startCountDown();
    }

    private void handleRegister() {
        clearErrorInfo();
        mPresenter.handleRegister(mEditTextCaptcha.getText().toString(),
                mPhoneNumber,
                mEditTextUserName.getText().toString(),
                mEditTextPassword.getText().toString(),
                mEditTextPasswordConfirm.getText().toString());
    }

    private void clearErrorInfo() {
        mEditTextCaptcha.setError(null);
        mEditTextUserName.setError(null);
        mEditTextPassword.setError(null);
        mEditTextPasswordConfirm.setError(null);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        SnackbarUtil.showLong(getActivity(), message);
    }

    @Override
    public void showConfirmRegisterView(String phone) {

    }

    @Override
    public void showCountDownTick(String secondsLeft) {
        if (mTextViewSendAgain != null) {
            mTextViewSendAgain.setText(secondsLeft);
        }
    }

    @Override
    public void showCountDownFinish() {
        if (mTextViewSendAgain != null) {
            mTextViewSendAgain.setEnabled(true);
            mTextViewSendAgain.setText(R.string.msg_send_again);
        }
    }

    @Override
    public void showParameterError(int index, int errorMsgResId) {
        switch (index) {
            case PARAMETER_ERROR_CAPTCHA:
                mEditTextCaptcha.requestFocus();
                mEditTextCaptcha.setError(getString(errorMsgResId));
                break;
            case PARAMETER_ERROR_USER_NAME:
                mEditTextUserName.requestFocus();
                mEditTextUserName.setError(getString(errorMsgResId));
                break;
            case PARAMETER_ERROR_PASSWORD:
                mEditTextPassword.requestFocus();
                mEditTextPassword.setError(getString(errorMsgResId));
                break;
            case PARAMETER_ERROR_DIFFERENT_PASSWORD:
                mEditTextPasswordConfirm.requestFocus();
                mEditTextPasswordConfirm.setError(getString(errorMsgResId));
                break;
        }
    }

    @Override
    public void showRegisterSuccess() {
        HomeActivity.launch(getActivity(), getString(R.string.msg_register_success));
        ((RegisterActivity) getActivity()).finishSelf();
    }
}
