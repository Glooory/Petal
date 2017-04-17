package com.glooory.petal.mvp.presenter;

import android.os.CountDownTimer;

import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.mvp.model.entity.register.CaptchaResult;
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
}
