package com.glooory.petal.mvp.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.rx.AnimOnSubscribe;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.entity.login.TokenBean;
import com.glooory.petal.mvp.ui.home.HomeActivity;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;
import common.PetalApplication;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/4.
 */

public class SplashActivity extends BasePetalActivity {

    @BindView(R.id.imageview_splash)
    SimpleDraweeView mImgSplash;
    @BindView(R.id.imageview_splash_logo)
    ImageView mImgLogo;

    private boolean isSkipLogin;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mImgSplash.setAspectRatio(0.5625F);
    }

    @Override
    protected void initData() {
        PetalApplication.getContext().getAppComponent().imageLoader()
                .loadImage(SplashActivity.this,
                        FrescoImageConfig.builder()
                                .setUrl("res:///" + R.drawable.bg_splash_screen_bulb)
                                .setSimpleDraweeView(mImgSplash)
                                .build());
    }

    @Override
    protected void onResume() {
        super.onResume();

        Animator scaleAnimator = AnimatorInflater
                .loadAnimator(this, R.animator.animator_splash_image_scale);
        final Animator alphaAnimator = AnimatorInflater
                .loadAnimator(this, R.animator.animator_splash_logo_alpha);
        scaleAnimator.setTarget(mImgSplash);
        alphaAnimator.setTarget(mImgLogo);
        alphaAnimator.setStartDelay(500);

        Observable.create(new AnimOnSubscribe(scaleAnimator))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        alphaAnimator.start();
                    }
                })
                .observeOn(Schedulers.io())
                .filter(new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(Void aVoid) {
                        isSkipLogin = (boolean) SPUtils.get(Constants.PREF_IS_SKIP_LOGIN, false);
                        return isLogin();
                    }
                })
                .filter(new Func1<Void, Boolean>() {
                    @Override
                    public Boolean call(Void aVoid) {
                        int tokenExpires = (int) SPUtils.get(Constants.PREF_TOKEN_EXPIRES_IN, 0);
                        long lastLoginTime = (long) SPUtils.get(Constants.PREF_LOGIN_TIME, 0L);
                        long loginTimeDiff = (System.currentTimeMillis() - lastLoginTime) / 1000;
                        Logger.d("Token expires:" + tokenExpires);
                        Logger.d("Last login at:" + lastLoginTime + "****" + "login time difference" + loginTimeDiff);
                        return loginTimeDiff > tokenExpires;
                    }
                })
                .flatMap(new Func1<Void, Observable<TokenBean>>() {
                    @Override
                    public Observable<TokenBean> call(Void aVoid) {
                        String refresh_token = (String) SPUtils.get(Constants.PREF_REFRESH_TOKEN, "");
                        return ((PetalApplication) mApplication)
                                .getAppComponent()
                                .serviceManager()
                                .getUserService()
                                .refreshToken(getString(R.string.url_request_token),
                                        Constants.GRANT_TYPE_REFRESH_TOKEN,
                                        refresh_token);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {
                        if (isLogin()) {
                            HomeActivity.launch(SplashActivity.this);
                        } else {
                            if (isSkipLogin) {
                                HomeActivity.launch(SplashActivity.this);
                            } else {
                                LoginActivity.launch(SplashActivity.this, true);
                            }
                        }
                        finishSelf();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoginActivity.launch(SplashActivity.this, true);
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {
                        saveToken(tokenBean);
                    }
                });
    }

    private void saveToken(TokenBean tokenBean) {
        SPUtils.builder()
                .addData(Constants.PREF_LOGIN_TIME, System.currentTimeMillis())
                .addData(Constants.PREF_ACCESS_TOKEN, tokenBean.getAccessToken())
                .addData(Constants.PREF_TOKEN_TYPE, tokenBean.getTokenType())
                .addData(Constants.PREF_TOKEN_EXPIRES_IN, tokenBean.getExpiresIn())
                .addData(Constants.PREF_REFRESH_TOKEN, tokenBean.getRefreshToken())
                .build();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //屏蔽返回键点击事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
