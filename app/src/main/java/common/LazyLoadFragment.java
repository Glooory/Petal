package common;

import android.os.Bundle;
import android.view.View;

import com.jess.arms.mvp.Presenter;

/**
 * Created by Glooory on 17/4/24.
 */

public abstract class LazyLoadFragment<P extends Presenter> extends BasePetalFragment<P> {

    /**
     * 标志 Fragment 的视图是否已经初始化完毕
     */
    private boolean mIsViewPrepared;
    /**
     * 标志是否已经触发过懒加载数据
     */
    private boolean mHasFetchedData;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见 && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && !mHasFetchedData && mIsViewPrepared) {
            mHasFetchedData = true;
            lazyFetchData();
        }
    }

    /**
     * 懒加载的方式获取数据，
     * 仅在满足 Fragment 可见和视图已经准备好的时候调一次
     */
    protected void lazyFetchData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // View 被销毁后，将可以重新触发数据加载，因为在 ViewPager 里， Fragment 不会被再次新建并走 onCreate（）
        // 的生命周期流程,将从 onCreateView() 开始
        mHasFetchedData = false;
        mIsViewPrepared = false;
    }
}
