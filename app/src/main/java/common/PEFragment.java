package common;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.Presenter;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by jess on 8/5/16 14:11
 * contact with jess.yan.effort@gmail.com
 */
public abstract class PEFragment<P extends Presenter> extends BaseFragment<P> {
    protected PEApplication mPEApplication;
    @Override
    protected void ComponentInject() {
        mPEApplication = (PEApplication)mActivity.getApplication();
        setupFragmentComponent(mPEApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher watcher = PEApplication.getRefWatcher(getActivity());//使用leakCanary检测fragment的内存泄漏
        if (watcher != null) {
            watcher.watch(this);
        }
        this.mPEApplication =null;
    }
}
