package com.glooory.petal.mvp.ui.searchresult;

import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchPinListBean;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Glooory on 17/3/31.
 */

public interface SearchResultContract {

    interface View extends BaseView {

        void showTabTitles(String[] titles);

        void showLoadingMore();

        void showNoMoreDataFooter(boolean showAnyway);

        void showLoginNav();
    }

    interface Model extends IModel {

        Observable<SearchPinListBean> getSearchedPins(String keyword);

        Observable<List<PinBean>> getSearchedPinsMore(String keyword);
    }
}
