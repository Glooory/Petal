package common;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.Presenter;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Glooory on 17/2/25
 */

public abstract class BasePetalFragment<P extends Presenter> extends BaseFragment<P> {

    @Override
    protected void componentInject() {
        setupFragmentComponent(PetalApplication.getContext().getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Override
    public void onDestroy() {
        RefWatcher watcher = PetalApplication.getRefWatcher(getActivity());//使用leakCanary检测fragment的内存泄漏
        if (watcher != null) {
            watcher.watch(this);
        }
        super.onDestroy();
    }
}
