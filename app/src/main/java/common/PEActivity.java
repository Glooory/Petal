package common;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.BaseClientInfo;
import com.glooory.petal.app.util.SPUtils;
import com.jess.arms.base.BaseActivity;

/**
 * Created by Glooory on 17/2/17
 */

public abstract class PEActivity<P extends PEPresenter> extends BaseActivity<P> {

    protected PEApplication mPEApplication;
    protected int mScreenPixelsWidth;
    protected boolean isLogin = false;
    protected String mAuthorization;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSharedElementTransition();
        isLogin = isLogin();
        mScreenPixelsWidth = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = isLogin();
        mAuthorization = getAuthorization();
    }

    public boolean isLogin() {
        return (boolean) SPUtils.get(Constants.PREF_IS_LOGIN, false);
    }

    public String getAuthorization() {
        if (isLogin()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(SPUtils.get(Constants.PREF_TOKEN_TYPE, " "))
                    .append(" ")
                    .append(SPUtils.get(Constants.PREF_TOKEN_ACCESS, " "));
            return stringBuilder.toString();
        }
        return BaseClientInfo.CLIENT_INFO_DEFAULT;
    }

    //Fresco shared element transition 已经解决的bug 调用以下方法
    private void setupSharedElementTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.CENTER_CROP, ScalingUtils.ScaleType.FIT_CENTER));
            getWindow().setSharedElementReturnTransition(DraweeTransition.createTransitionSet(
                    ScalingUtils.ScaleType.FIT_CENTER, ScalingUtils.ScaleType.CENTER_CROP));
        }
    }

    @Override
    protected void componentInject() {
        mPEApplication = (PEApplication) getApplication();
        setupActivityComponent(mPEApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPEApplication = null;
    }

    public void finishSelf() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.finishAfterTransition();
        } else {
            this.finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishSelf();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
