package com.glooory.petal.app.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.glooory.petal.R;

/**
 * Created by Glooory on 17/2/25.
 */

public class WindmillLoadMoreFooter extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_footer_loading_more;
    }

    @Override
    public boolean isLoadEndGone() {
        return true;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_loading_more;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.textview_load_more_failed;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
