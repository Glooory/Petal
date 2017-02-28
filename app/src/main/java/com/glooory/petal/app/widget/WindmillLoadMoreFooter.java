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
    protected int getLoadingViewId() {
        return R.id.progressbar_loadmore;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.layout.view_footer_load_failed;
    }

    @Override
    protected int getLoadEndViewId() {
        return 0;
    }
}
