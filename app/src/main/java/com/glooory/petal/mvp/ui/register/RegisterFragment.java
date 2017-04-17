package com.glooory.petal.mvp.ui.register;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.glooory.petal.R;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.di.component.DaggerRegisterComponent;
import com.glooory.petal.di.module.RegisterModule;
import com.glooory.petal.mvp.presenter.RegisterPresenter;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalFragment;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Glooory on 17/4/14.
 */

public class RegisterFragment extends BasePetalFragment<RegisterPresenter>
        implements RegisterContract.View{


    @BindView(R.id.edit_text_register_phone_number)
    EditText mEditTextPhoneNumber;
    @BindView(R.id.button_register_next)
    Button mButtonRegisterNext;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
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
    protected void setupViews() {
        RxTextView.editorActions(mEditTextPhoneNumber, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_NEXT;
            }
        }).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        handleRegister();
                    }
                });

        RxView.clicks(mButtonRegisterNext)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        handleRegister();
                    }
                });
    }

    @Override
    protected View initView(ViewGroup container) {
        mRootView = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_register, container, false);
        return mRootView;
    }

    @Override
    protected void initData() {

    }

    private void handleRegister() {
        String phoneNumber = mEditTextPhoneNumber.getText().toString().trim();
        mPresenter.performRegister(phoneNumber);
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
        ((RegisterActivity) getActivity()).showRegisterConfirmFragment(phone);
    }

    @Override
    public void showCountDownTick(String secondsLeft) {

    }

    @Override
    public void showCountDownFinish() {

    }
}
