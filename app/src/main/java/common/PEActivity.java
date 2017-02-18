package common;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.DraweeTransition;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.Presenter;

/**
 * Created by jess on 8/5/16 13:13
 * contact with jess.yan.effort@gmail.com
 */
public abstract class PEActivity<P extends Presenter> extends BaseActivity<P> {

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
        mAuthorization = getAuthorization(isLogin);
    }

    public boolean isLogin() {
        // TODO: 17/2/18 Check login state from shared preference
        return false;
    }

    public String getAuthorization(boolean isLogin) {
        // TODO: 17/2/18 Get the authorization from shared preference file.
        return "";
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
    protected void ComponentInject() {
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
            finishAfterTransition();
        } else {
            finish();
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
