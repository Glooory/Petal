package common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glooory.petal.R;
import com.jess.arms.mvp.Presenter;

import butterknife.BindView;

/**
 * Created by Glooory on 17/4/7.
 */

public abstract class BaseRecyclerFragment<P extends Presenter> extends BasePetalFragment<P> {

    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    protected View mNoMoreDataFooter;

    @Override
    protected View initView(ViewGroup container) {
        mRootView = LayoutInflater.from(getActivity())
                .inflate(R.layout.view_recycler_view, container, false);
        mNoMoreDataFooter = LayoutInflater.from(getActivity())
                .inflate(R.layout.view_footer_no_more_data, null);
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoMoreDataFooter = null;
    }
}
