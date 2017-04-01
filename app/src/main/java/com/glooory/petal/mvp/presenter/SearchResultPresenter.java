package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.widget.WindmillLoadMoreFooter;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.glooory.petal.mvp.model.entity.board.FollowBoardResultBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchBoardListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchPinListBean;
import com.glooory.petal.mvp.model.entity.searchresult.SearchUserListBean;
import com.glooory.petal.mvp.ui.board.BoardActivity;
import com.glooory.petal.mvp.ui.home.HomePinAdapter;
import com.glooory.petal.mvp.ui.pindetail.PinDetailActivity;
import com.glooory.petal.mvp.ui.searchresult.SearchResultContract;
import com.glooory.petal.mvp.ui.category.board.CategoryBoardAdapter;
import com.glooory.petal.mvp.ui.searchresult.user.SearchUserAdapter;
import com.glooory.petal.mvp.ui.user.UserActivity;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.BasePetalPresenter;
import common.PetalApplication;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/31.
 */
@FragmentScope
public class SearchResultPresenter extends BasePetalPresenter<SearchResultContract.View, SearchResultContract.Model> {

    private BasePetalAdapter mAdapter;
    private String mSearchKeyword;
    private int mPinCount;
    private int mBoardCount;
    private int mUserCount;

    @Inject
    public SearchResultPresenter(SearchResultContract.View rootView, SearchResultContract.Model model) {
        super(rootView, model);
    }

    public void setAdapter(BasePetalAdapter adapter) {
        mAdapter = adapter;
        initAdapter();
    }

    private void initAdapter() {
        mAdapter.setLoadMoreView(new WindmillLoadMoreFooter());
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mRootView.showLoadingMore();
            }
        });
    }

    public void getSearchedPins(String keyword) {
        mSearchKeyword = keyword;
        mModel.getSearchedPins(keyword)
                .map(new Func1<SearchPinListBean, List<PinBean>>() {
                    @Override
                    public List<PinBean> call(SearchPinListBean searchPinListBean) {
                        updateTabTitles(searchPinListBean);
                        return searchPinListBean.getPins();
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        if (pinBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter(true);
                            return;
                        }
                        mAdapter.setNewData(pinBeen);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getSearchedPinsMore() {
        mModel.getSearchedPinsMore(mSearchKeyword)
                .compose(RxUtils.<List<PinBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<PinBean>>() {
                    @Override
                    public void onNext(List<PinBean> pinBeen) {
                        if (pinBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter(true);
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        mAdapter.addData(pinBeen);
                        mAdapter.loadMoreComplete();
                        mRootView.showNoMoreDataFooter(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    private void updateTabTitles(SearchPinListBean searchPinListBean) {
        if (searchPinListBean == null) {
            return;
        }

        Resources resources = PetalApplication.getContext().getResources();
        String mPinCountFormat = resources.getString(R.string.format_collection_count);
        String mBoardCountFormat = resources.getString(R.string.format_board_count);
        String mUserCountFormat = resources.getString(R.string.format_user_count);
        mPinCount = searchPinListBean.getPinCount();
        String[] titles = new String[3];
        titles[0] = String.format(mPinCountFormat, String.valueOf(mPinCount));
        titles[1] = String.format(mBoardCountFormat, searchPinListBean.getBoardCount());
        titles[2] = String.format(mUserCountFormat, searchPinListBean.getPeopleCount());
        mRootView.showTabTitles(titles);
    }

    public void launchPinDetailActivity(Activity activity, View view, int position) {
        PinBean pinBean = ((HomePinAdapter) mAdapter).getItem(position);
        float aspectRatio = pinBean.getFile().getWidth() /
                (float) pinBean.getFile().getHeight();
        if (aspectRatio < 0.3) {
            aspectRatio = 0.3F;
        }
        PinDetailActivity.launch(activity,
                pinBean.getPinId(),
                aspectRatio,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin),
                DrawableUtils.getBasicColorStr(((HomePinAdapter) mAdapter).getItem(position)));
    }

    /**
     * Launch UserActivity, 仅当用户点击的是采集底部的用户头像时
     * @param activity
     * @param view
     * @param position
     */
    public void launchUserActivityFromPin(Activity activity, View view, int position) {
        PinBean pinBean = ((HomePinAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(pinBean.getUserId());
        UserActivity.launch(activity,
                userId,
                pinBean.getUser().getUsername(),
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_pin_avatar));
    }

    public void getSearchedBoards(String keyword) {
        mSearchKeyword = keyword;
        mModel.getSearchedBoards(keyword)
                .map(new Func1<SearchBoardListBean, List<BoardBean>>() {
                    @Override
                    public List<BoardBean> call(SearchBoardListBean searchBoardListBean) {
                        mBoardCount = searchBoardListBean.getBoardCount();
                        return searchBoardListBean.getBoards();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBean) {
                        if (boardBean.size() == 0) {
                            mRootView.showNoMoreDataFooter(true);
                            return;
                        }
                        mAdapter.setNewData(boardBean);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });

    }

    public void getSearchedBoardsMore() {
        mModel.getSearchedBoardsMore(mSearchKeyword)
                .compose(RxUtils.<List<BoardBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<BoardBean>>() {
                    @Override
                    public void onNext(List<BoardBean> boardBeanList) {
                        if (boardBeanList.size() == 0) {
                            mRootView.showNoMoreDataFooter(true);
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        mAdapter.addData(boardBeanList);
                        mRootView.showNoMoreDataFooter(false);
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void launchBoardActivity(Activity activity, int position) {
        final BoardBean boardBean = ((CategoryBoardAdapter) mAdapter).getItem(position);
        boardBean.setDeleting(1);
        BoardActivity.launch(activity, boardBean.getUser().getUsername(), boardBean);
    }

    public void launchUserActivityFromBoard(Activity activity, View view, int position) {
        BoardBean boardBean = ((CategoryBoardAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(boardBean.getUserId());
        String userName = boardBean.getUser().getUsername();
        UserActivity.launch(activity, userId, userName,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_view_category_board_user_avatar));
    }

    /**
     * 画板 item 上的底部操作按钮的点击事件
     * @param position
     */
    public void onBoardOperateBtnClick(int position) {
        if (!mModel.isLogin()) {
            mRootView.showLoginNav();
            return;
        }
        actionFollowBoard(position);
    }

    /**
     * 关注或者取消关注画板
     * @param position
     */
    private void actionFollowBoard(final int position) {
        final BoardBean boardBean = ((CategoryBoardAdapter) mAdapter).getItem(position);
        String boardId = String.valueOf(boardBean.getBoardId());
        final boolean isFollowed = boardBean.isFollowing();
        mModel.followBoard(boardId, isFollowed)
                .compose(RxUtils.<FollowBoardResultBean>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<FollowBoardResultBean>() {
                    @Override
                    public void onNext(FollowBoardResultBean followBoardResultBean) {
                        boolean isFollowedTemp = !boardBean.isFollowing();
                        boardBean.setFollowing(isFollowedTemp);
                        int followerCount = boardBean.getFollowCount();
                        boardBean.setFollowCount(isFollowed ? --followerCount : ++followerCount);
                        mAdapter.notifyItemChanged(position);
                    }
                });
    }

    public void getSearchedUsers(String keyword) {
        mSearchKeyword = keyword;
        mModel.getSearchedUsers(mSearchKeyword)
                .compose(RxUtils.<SearchUserListBean>bindToLifecycle(mRootView))
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();
                    }
                })
                .map(new Func1<SearchUserListBean, List<UserBean>>() {
                    @Override
                    public List<UserBean> call(SearchUserListBean searchUserListBean) {
                        mUserCount = searchUserListBean.getPeopleCount();
                        return searchUserListBean.getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> userBeen) {
                        if (userBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter(true);
                            return;
                        }
                        mAdapter.setNewData(userBeen);
                        mRootView.showNoMoreDataFooter(false);
                    }
                });
    }

    public void getSearchedUsersMore() {
        mModel.getSearchedUsersMore(mSearchKeyword)
                .compose(RxUtils.<List<UserBean>>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<List<UserBean>>() {
                    @Override
                    public void onNext(List<UserBean> userBeen) {
                        if (userBeen.size() == 0) {
                            mRootView.showNoMoreDataFooter(true);
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        mAdapter.addData(userBeen);
                        mAdapter.loadMoreComplete();
                        mRootView.showNoMoreDataFooter(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void launchUserActivityFromUser(FragmentActivity activity, View view, int position) {
        UserBean userBean = ((SearchUserAdapter) mAdapter).getItem(position);
        String userId = String.valueOf(userBean.getUserId());
        String userName = userBean.getUsername();
        UserActivity.launch(activity, userId, userName,
                (SimpleDraweeView) view.findViewById(R.id.simple_drawee_card_user_avatar));
    }

    public void onUserOperateBtnClick(int position) {
        if (!mModel.isLogin()) {
            mRootView.showLoginNav();
            return;
        }
        actionFollowUser(position);
    }

    private void actionFollowUser(final int postion) {
        final UserBean userBean = ((SearchUserAdapter) mAdapter).getItem(postion);
        String userId = String.valueOf(userBean.getUserId());
        final boolean isFollowed = userBean.getFollowing() == 1;
        mModel.followUser(userId, isFollowed)
                .compose(RxUtils.<Void>bindToLifecycle(mRootView))
                .subscribe(new BaseSubscriber<Void>() {
                    @Override
                    public void onNext(Void aVoid) {
                        int followerCount = userBean.getFollowerCount();
                        userBean.setFollowerCount(isFollowed ? --followerCount : ++followerCount);
                        ((SearchUserAdapter) mAdapter).getItem(postion)
                                .setFollowing(isFollowed ? 0 : 1);
                        mAdapter.notifyItemChanged(postion);
                    }
                });
    }

    public int getPinCount() {
        return mPinCount;
    }

    public int getBoardCount() {
        return mBoardCount;
    }

    public int getUserCount() {
        return mUserCount;
    }
}
