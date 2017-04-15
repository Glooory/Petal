package com.glooory.petal.mvp.presenter;

import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.mvp.ui.register.RegisterContract;
import com.jess.arms.utils.RxUtils;

import javax.inject.Inject;

import common.BasePetalPresenter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/4/14.
 */

public class RegisterPresenter extends BasePetalPresenter<RegisterContract.View, RegisterContract.Model> {

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
                .compose(RxUtils.<Void>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        mRootView.showConfirmRegisterView(phone);
                    }
                });
    }
}
