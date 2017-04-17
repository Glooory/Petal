package com.glooory.petal.mvp.ui.register;

import com.glooory.petal.mvp.model.entity.register.CaptchaResult;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import rx.Observable;

/**
 * Created by Glooory on 17/4/14.
 */

public interface RegisterContract {

    interface View extends BaseView {

        void showConfirmRegisterView(String phone);

        void showCountDownTick(String secondsLeft);

        void showCountDownFinish();
    }

    interface Model extends IModel {

        boolean isPhoneNumber(String number);

        Observable<CaptchaResult> register(String phoneNumber);
    }
}
