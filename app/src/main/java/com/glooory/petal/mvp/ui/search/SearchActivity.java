package com.glooory.petal.mvp.ui.search;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.adapter.NoFilterringAdapter;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.app.util.UIUtils;
import com.glooory.petal.app.widget.FlowLayout;
import com.glooory.petal.mvp.model.entity.search.SearchHintBean;
import com.glooory.petal.mvp.ui.category.CategoryActivity;
import com.glooory.petal.mvp.ui.searchresult.SearchResultActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;
import common.PetalApplication;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Glooory on 17/3/29.
 */

public class SearchActivity extends BasePetalActivity {

    private static final int RAW_COUNT = 3;
    private static final int ITEM_MARGIN = 1;
    private static final int ITEM_VERTICAL_MARGIN = 8;
    private static final int ITEM_HORIZATAL_MARGIN = 16;

    @BindView(R.id.actv_search)
    AutoCompleteTextView mActvSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.imgbtn_clear_history)
    ImageButton mImgBtnClearHistory;
    @BindView(R.id.flow_layout_history)
    FlowLayout mFlowLayoutHistory;
    @BindView(R.id.flow_layout_category)
    FlowLayout mFlowLayoutCategory;

    @BindString(R.string.msg_empty_search_history)
    String mStrEmptySearchHistory;

    private int mItemWidth;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> mSuggestionList = new ArrayList<>(0);

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SearchActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {

        mImgBtnClearHistory.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_close_grey_500_24dp));

        initCategoryFlowLayout();
        initSuggestionAdapter();
        initToolbar();

        RxView.clicks(mImgBtnClearHistory)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        clearSearchHistory();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showSearchHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                actionSearch();
                return true;
            case R.id.action_clear_search_history:
                clearSearchHistory();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCategoryFlowLayout() {
        String[] categoryNames = getResources().getStringArray(R.array.category_names);
        String[] categoryValues = getResources().getStringArray(R.array.category_value);

        mItemWidth = UIUtils.getScreenWidth(this) / RAW_COUNT - ITEM_MARGIN * (RAW_COUNT - 1);

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = ITEM_MARGIN;
        layoutParams.topMargin = ITEM_MARGIN;
        layoutParams.rightMargin = ITEM_MARGIN;
        layoutParams.bottomMargin = ITEM_MARGIN;
        for (int i = 0; i < categoryNames.length; i++) {
            addCategorySubitem(categoryNames[i], categoryValues[i], i, layoutParams);
        }
    }

    private void initSuggestionAdapter() {
        mAdapter = new NoFilterringAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, mSuggestionList);
        mActvSearch.setAdapter(mAdapter);
    }

    /**
     * 监听用户的输入，展示关键字的联想
     */
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RxTextView.textChanges(mActvSearch)
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() > 0;
                    }
                })
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMap(new Func1<CharSequence, Observable<SearchHintBean>>() {
                    @Override
                    public Observable<SearchHintBean> call(CharSequence charSequence) {
                        return ((PetalApplication) PetalApplication.getContext())
                                .getAppComponent()
                                .serviceManager()
                                .getSearchService()
                                .getSearchHint(charSequence.toString().trim());
                    }
                })
                .map(new Func1<SearchHintBean, List<String>>() {
                    @Override
                    public List<String> call(SearchHintBean searchHintBean) {
                        return searchHintBean.getResult();
                    }
                })
                .filter(new Func1<List<String>, Boolean>() {
                    @Override
                    public Boolean call(List<String> strings) {
                        return strings != null && strings.size() > 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mSuggestionList.clear();
                        mSuggestionList.addAll(strings);
                        mAdapter.notifyDataSetChanged();
                    }
                });
        
        mActvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchSearchResultActivity();
            }
        });

        RxTextView.editorActions(mActvSearch, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer == EditorInfo.IME_ACTION_SEARCH;
            }
        })
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        actionSearch();
                    }
                });
    }

    /**
     * 添加所有分类的 btn
     *
     * @param categoryName
     * @param categoryValue
     * @param position
     * @param layoutParams
     */
    private void addCategorySubitem(final String categoryName, final String categoryValue, int position,
            ViewGroup.MarginLayoutParams layoutParams) {
        Button categoryBtn = new Button(this);
        int drawbleResId = Constants.CATEGORY_ICON_RES_IDS[position];
        categoryBtn.setCompoundDrawablesWithIntrinsicBounds(
                null,
                DrawableUtils.getTintListDrawable(SearchActivity.this, drawbleResId, R.color.tint_list_pink),
                null,
                null
        );
        categoryBtn.setText(categoryName);
        categoryBtn.setBackgroundColor(Color.WHITE);
        categoryBtn.setLayoutParams(layoutParams);
        categoryBtn.setGravity(Gravity.CENTER);

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryActivity.launch(SearchActivity.this, categoryName, categoryValue);
            }
        });

        mFlowLayoutCategory.addView(categoryBtn);
    }

    private void showEmptySearchHistory() {
        TextView emptyHistoryTv = new TextView(this);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup
                .MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(ITEM_HORIZATAL_MARGIN, ITEM_MARGIN, ITEM_MARGIN, ITEM_MARGIN);
        emptyHistoryTv.setText(mStrEmptySearchHistory);
        emptyHistoryTv.setLayoutParams(layoutParams);
        emptyHistoryTv.setGravity(Gravity.START);
        mFlowLayoutHistory.addView(emptyHistoryTv);
    }

    private void showSearchHistory() {
        mFlowLayoutHistory.removeAllViews();

        String searchHistory = (String) SPUtils.get(Constants.PREF_SEARCH_HISTORY, "");
        String[] searchHistories = searchHistory.split(Constants.COMMA);

        if (searchHistories == null || searchHistories.length == 0) {
            showEmptySearchHistory();
        } else if (searchHistories.length == 1 && TextUtils.isEmpty(searchHistories[0])) {
            showEmptySearchHistory();
        } else {
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.leftMargin = ITEM_HORIZATAL_MARGIN;
            layoutParams.topMargin = ITEM_VERTICAL_MARGIN;
            layoutParams.bottomMargin = ITEM_HORIZATAL_MARGIN;
            for (int i = 0; i < searchHistories.length; i++) {
                addHistorySubitem(searchHistories[i], layoutParams);
            }
        }
    }

    /**
     * 添加搜索记录
     * @param text
     */
    private void addHistorySubitem(final String text, ViewGroup.MarginLayoutParams layoutParams) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        TextView textView = new TextView(this);
        textView.setPadding(ITEM_HORIZATAL_MARGIN, ITEM_VERTICAL_MARGIN, ITEM_HORIZATAL_MARGIN, ITEM_VERTICAL_MARGIN);
        textView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_text_search_history));
        textView.setLayoutParams(layoutParams);
        textView.setText(text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchResultActivity.launch(SearchActivity.this, text);
            }
        });
        mFlowLayoutHistory.addView(textView);
    }

    /**
     * 清空搜索历史记录
     */
    private void clearSearchHistory() {
        mFlowLayoutHistory.removeAllViews();
        SPUtils.remove(Constants.PREF_SEARCH_HISTORY);
        showEmptySearchHistory();
    }

    private void actionSearch() {
        if (mActvSearch.getText().toString().trim().length() > 0) {
            launchSearchResultActivity();
        }
    }

    private void launchSearchResultActivity() {
        SearchResultActivity.launch(SearchActivity.this, mActvSearch.getText().toString().trim());
    }
}
