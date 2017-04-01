package com.glooory.petal.mvp.ui.category;

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
import com.glooory.petal.mvp.ui.category.board.CategoryBoardFragment;
import com.glooory.petal.mvp.ui.category.pin.CategoryPinFragment;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/4/1.
 */

public class CategoryActivity extends BasePetalActivity
        implements SwipeRefreshLayout.OnRefreshListener{

    private static final String EXTRA_CATEGORY_NAME = "category_name";
    private static final String EXTRA_CATEGORY_VALUE = "category_value";

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
    @BindArray(R.array.category_subitem_titles)
    String[] mTabTitles;

    private CategorySectionAdapter mPagerAdapter;
    private String mCategoryName;
    private String mCategoryValue;
    private CategoryPinFragment mPinFragment;
    private CategoryBoardFragment mBoardFragment;

    public static void launch(Activity activity, String categoryName, String categoryValue) {
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        intent.putExtra(EXTRA_CATEGORY_VALUE, categoryValue);
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
                ContextCompat.getColor(CategoryActivity.this, R.color.red_google_icon),
                ContextCompat.getColor(CategoryActivity.this, R.color.blue_google_icon),
                ContextCompat.getColor(CategoryActivity.this, R.color.yellow_google_icon),
                ContextCompat.getColor(CategoryActivity.this, R.color.green_google_icon)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mTablayout.setSelectedTabIndicatorColor(mTabIndicatorColor);
        mTabTitles = getResources().getStringArray(R.array.search_result_subitem_titles);
        initViewPager();
    }

    @Override
    protected void initData() {
        mCategoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);
        mCategoryValue = getIntent().getStringExtra(EXTRA_CATEGORY_VALUE);
        getSupportActionBar().setTitle(mCategoryName);
    }

    private void initViewPager() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new CategorySectionAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(0, true);
            mTablayout.setupWithViewPager(mViewPager);
        }
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

    @Override
    public void onRefresh() {
        switch (mViewPager.getCurrentItem()) {
            case 0:
                if (mPinFragment != null) {
                    mPinFragment.onRefresh();
                }
                break;
            case 1:
                if (mBoardFragment != null) {
                    mBoardFragment.onRefresh();
                }
                break;
            case 2:

                break;
        }
    }

    class CategorySectionAdapter extends FragmentStatePagerAdapter {

        public CategorySectionAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CategoryPinFragment.newInstance(mCategoryValue);
                case 1:
                    return CategoryBoardFragment.newInstance(mCategoryValue);
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            switch (position) {
                case 0:
                    mPinFragment = (CategoryPinFragment) createdFragment;
                    break;
                case 1:
                    mBoardFragment = (CategoryBoardFragment) createdFragment;
                    break;
                case 2:

                    break;
            }
            return createdFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

}
