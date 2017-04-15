package com.glooory.petal.mvp.ui.register;

import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import rx.Observable;

/**
 * Created by Glooory on 17/4/14.
 */

public interface RegisterContract {

    interface View extends BaseView {

        void showConfirmRegisterView(String phone);
    }

    interface Model extends IModel {

        boolean isPhoneNumber(String number);

        Observable<Void> register(String phoneNumber);
    }
}
