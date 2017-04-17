package com.glooory.petal.mvp.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.glooory.petal.R;
import com.glooory.petal.di.component.DaggerRegisterComponent;
import com.glooory.petal.di.module.RegisterModule;
import com.glooory.petal.mvp.presenter.RegisterPresenter;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/4/17.
 */

public class RegisterConfirmFragment extends BasePetalFragment<RegisterPresenter>
        implements RegisterContract.View{

    private static final String ARGS_PHONE_NUMBER = "phone";

    @BindView(R.id.text_view_register_phone)
    TextView mTextViewPhone;
    @BindView(R.id.edit_text_register_captcha)
    EditText mEditTextCaptcha;
    @BindView(R.id.text_view_register_send_again)
    TextView mTextViewSendAgain;
    @BindView(R.id.edit_text_register_password)
    EditText mEditTextPassword;
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

        mPresenter.startCountDown();
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

    }

    @Override
    public void showConfirmRegisterView(String phone) {

    }

    @Override
    public void showCountDownTick(String secondsLeft) {
        mTextViewSendAgain.setText(secondsLeft);
    }

    @Override
    public void showCountDownFinish() {
        mTextViewSendAgain.setEnabled(true);
        mTextViewSendAgain.setText(R.string.msg_send_again);
    }
}
