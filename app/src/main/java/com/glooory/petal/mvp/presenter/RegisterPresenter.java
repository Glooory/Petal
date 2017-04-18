package com.glooory.petal.mvp.presenter;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.register.CaptchaResult;
import com.glooory.petal.mvp.model.entity.register.RegisterResultBean;
import com.glooory.petal.mvp.ui.register.RegisterConfirmFragment;
import com.glooory.petal.mvp.ui.register.RegisterContract;
import com.jess.arms.utils.RxUtils;

import javax.inject.Inject;

import common.BasePetalPresenter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/4/14.
 */

public class RegisterPresenter extends BasePetalPresenter<RegisterContract.View, RegisterContract.Model> {

    private int mIndentityCodeInterval = 60;
    private CountDownTimer mIdentityCodeCountDownTimer = new CountDownTimer(60000, 1000) {
        String countDownFormat = PetalApplication.getContext().getString(R.string.format_send_again);

        @Override
        public void onTick(long millisUntilFinished) {
            mIndentityCodeInterval--;
            String countDownHint = String.format(countDownFormat, mIndentityCodeInterval);
            mRootView.showCountDownTick(countDownHint);
        }

        @Override
        public void onFinish() {
            mRootView.showCountDownFinish();
        }
    };

    @Inject
    public RegisterPresenter(RegisterContract.View rootView, RegisterContract.Model model) {
        super(rootView, model);
    }

    public void performRegister(String phoneNumber) {
        if (mModel.isPhoneNumber(phoneNumber)) {
            register(phoneNumber);
        } else {
            mRootView.showMessage(PetalApplication.getContext().getString(R.string.msg_invalid_phone_number));
        }
    }

    private void register(final String phone) {
        mModel.register(phone)
                .compose(RxUtils.<CaptchaResult>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<CaptchaResult>() {
                    @Override
                    public void onNext(CaptchaResult captchaResult) {
                        if (captchaResult.getErr() == 0) {
                            mRootView.showConfirmRegisterView(phone);
                        } else {
                            mRootView.showMessage(captchaResult.getMsg());
                        }
                    }
                });
    }

    public void startCountDown() {
        mIndentityCodeInterval = 60;
        mIdentityCodeCountDownTimer.start();
    }

    public void sendIdentityCodeAgain(String phone) {
        performRegister(phone);
        startCountDown();
    }

    @Override
    public void onDestroy() {
        if (mIdentityCodeCountDownTimer != null) {
            mIdentityCodeCountDownTimer.cancel();
        }
        mIdentityCodeCountDownTimer = null;
        super.onDestroy();
    }

    public void onRegisterBtnClicked(String captcha, final String tel, String userName, String password,
            String confirmedPassword) {
        if (TextUtils.isEmpty(captcha)) {
            mRootView.showParameterError(RegisterConfirmFragment.PARAMETER_ERROR_CAPTCHA,
                    R.string.msg_captcha_is_empty);
            return;
        }

        if (TextUtils.isEmpty(userName)) {
            mRootView.showParameterError(RegisterConfirmFragment.PARAMETER_ERROR_USER_NAME,
                    R.string.msg_user_name_is_empty);
            return;
        }

        if (!isUserNameValid(userName)) {
            mRootView.showParameterError(RegisterConfirmFragment.PARAMETER_ERROR_USER_NAME,
                    R.string.msg_user_name_invalid);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mRootView.showParameterError(RegisterConfirmFragment.PARAMETER_ERROR_PASSWORD,
                    R.string.msg_password_is_empty);
            return;
        }

        if (password.length() < 6 || password.length() > 32) {
            mRootView.showParameterError(RegisterConfirmFragment.PARAMETER_ERROR_PASSWORD,
                    R.string.msg_password_invalid);
            return;
        }

        if (!password.equals(confirmedPassword)) {
            mRootView.showParameterError(RegisterConfirmFragment.PARAMETER_ERROR_DIFFERENT_PASSWORD,
                    R.string.msg_password_different);
            return;
        }

        mModel.signup(captcha, tel, userName, password)
                .compose(RxUtils.<RegisterResultBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<RegisterResultBean>() {
                    @Override
                    public void onNext(RegisterResultBean registerResultBean) {
                        if (registerResultBean.getErr() == 0) {
                            // TODO: 17/4/18 Save UserInfo token and userId
                            mModel.saveToken(registerResultBean);
                            requestUserMeInfo(tel);
                        } else {
                            mRootView.showMessage(registerResultBean.getMsg());
                        }
                    }
                });
    }

    /**
     * 请求用户自己的用户信息
     */
    private void requestUserMeInfo(final String userAccount) {
        mModel.requestUserMeInfo(userAccount)
                .subscribe(new BaseSubscriber<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        mModel.saveUserInfo(userBean, userAccount);
                    }
                });
    }

    public boolean isUserNameValid(String userName) {
        if (userName.contains("@") ||
                userName.contains("#") ||
                userName.contains("$") ||
                userName.contains(" ")) {
            return false;
        }
        return true;
    }
}
