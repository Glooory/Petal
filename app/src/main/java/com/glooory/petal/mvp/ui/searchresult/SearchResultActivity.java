package com.glooory.petal.mvp.ui.searchresult;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.ui.searchresult.pin.SearchPinFragment;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindColor;
import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/31.
 */

public class SearchResultActivity extends BasePetalActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private static final String EXTRA_SEARCH_KEYWORD = "search_keyword";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindColor(R.color.white)
    int mTabIndicatorColor;

    private String mSearchKeyword;
    private SearchResultSectionAdapter mPagerAdapter;
    private String[] mTabTitles;
    private SearchPinFragment mPinFragment;

    public static void launch(Activity activity, String keyword) {
        Intent intent = new Intent(activity, SearchResultActivity.class);
        intent.putExtra(EXTRA_SEARCH_KEYWORD, keyword);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(SearchResultActivity.this, R.color.red_google_icon),
                ContextCompat.getColor(SearchResultActivity.this, R.color.blue_google_icon),
                ContextCompat.getColor(SearchResultActivity.this, R.color.yellow_google_icon),
                ContextCompat.getColor(SearchResultActivity.this, R.color.green_google_icon)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mTablayout.setSelectedTabIndicatorColor(mTabIndicatorColor);
        mTabTitles = getResources().getStringArray(R.array.search_result_subitem_titles);
        initViewPager();
    }

    @Override
    protected void initData() {
        mSearchKeyword = getIntent().getStringExtra(EXTRA_SEARCH_KEYWORD);
        getSupportActionBar().setTitle(mSearchKeyword);
        saveSearchHistoryKeyword(mSearchKeyword);
    }

    @Override
    public void onRefresh() {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                if (mPinFragment != null) {
                    mPinFragment.onRefresh();
                }
                break;
        }
    }

    private void initViewPager() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new SearchResultSectionAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(0, true);
            mTablayout.setupWithViewPager(mViewPager);
        }
    }

    private void saveSearchHistoryKeyword(String keyword) {
        HashSet<String> histories = ((HashSet<String>) SPUtils.get(Constants.PREF_SEARCH_HISTORY, new HashSet<String>()));
        Set<String> newData = new HashSet<>(histories);
        newData.add(keyword);
        SPUtils.putByApply(Constants.PREF_SEARCH_HISTORY, newData);
    }

    public void showLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void hideLoading() {
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void setTabTitles(final String[] tabTitles) {
        mTabTitles = tabTitles;
        Observable.just(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mViewPager.getAdapter().notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    class SearchResultSectionAdapter extends FragmentStatePagerAdapter {

        public SearchResultSectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SearchPinFragment.newInstance(mSearchKeyword);
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createFragment = (Fragment) super.instantiateItem(container, position);
            switch (position) {
                case 0:
                    mPinFragment = (SearchPinFragment) createFragment;
                    break;
            }
            return createFragment;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }
}
