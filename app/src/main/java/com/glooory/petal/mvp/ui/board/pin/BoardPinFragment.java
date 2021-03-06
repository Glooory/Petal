package com.glooory.petal.mvp.ui.board.pin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.DialogUtils;
import com.glooory.petal.app.widget.CustomStaggeredGridLayoutManager;
import com.glooory.petal.di.component.DaggerBoardSectionComponent;
import com.glooory.petal.di.module.BoardSectionModule;
import com.glooory.petal.mvp.presenter.BoardSectionPresenter;
import com.glooory.petal.mvp.ui.board.BoardActivity;
import com.glooory.petal.mvp.ui.board.BoardContract;
import com.glooory.petal.mvp.ui.pindetail.EditPinDialogFragment;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import common.AppComponent;
import common.BaseRecyclerFragment;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/3/28.
 */

public class BoardPinFragment extends BaseRecyclerFragment<BoardSectionPresenter>
        implements BoardContract.SectionView{

    private static final String ARGS_PIN_COUNT = "pin_count";
    private static final String ARGS_IS_MINE = "is_mine";

    @Inject
    BoardPinAdapter mAdapter;
    private int mPinCount;
    private String mBoardId;
    private boolean mIsMine;

    public static BoardPinFragment newInstance(String boardId, int pinCount, boolean isMine) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_BOARD_ID, boardId);
        args.putInt(ARGS_PIN_COUNT, pinCount);
        args.putBoolean(ARGS_IS_MINE, isMine);
        BoardPinFragment fragment = new BoardPinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerBoardSectionComponent.builder()
                .appComponent(appComponent)
                .boardSectionModule(new BoardSectionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBoardId = getArguments().getString(Constants.EXTRA_BOARD_ID);
        mPinCount = getArguments().getInt(ARGS_PIN_COUNT);
        mIsMine = getArguments().getBoolean(ARGS_IS_MINE);
        mPresenter.setAdapter(mAdapter);
    }

    @Override
    protected void setupViews() {
        mRecyclerView.setLayoutManager(new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_pin_img:
                        mPresenter.launchPinDetailActivity(getActivity(), view, position);
                        break;
                    case R.id.ll_pin_via_info:
                        mPresenter.launchUserActivityFromPin(getActivity(), view, position);
                        break;
                }
            }
        });
        if (mIsMine) {
            mRecyclerView.addOnItemTouchListener(new OnItemChildLongClickListener() {
                @Override
                public void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                    mPresenter.onPinLongClick(position);
                }
            });
        }
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        if (mPinCount <= 0) {
            mAdapter.addFooterView(mNoMoreDataFooter);
        } else {
            mPresenter.getBoardPins(mBoardId);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) {
            mAdapter.removeAllFooterView();
        }
    }

    @Override
    public void onDestroy() {
        mAdapter = null;
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        ((BoardActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BoardActivity) getActivity()).hideLoading();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoadingMore() {
        if (mAdapter.getData().size() >= mPinCount) {
            return;
        }

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.getBoardPinsMore();
            }
        });
    }

    @Override
    public void showNoMoreDataFooter(boolean showAnyway) {
        if (showAnyway || mAdapter.getData().size() >= mPinCount) {
            if (mNoMoreDataFooter.getParent() != null) {
                ((ViewGroup) mNoMoreDataFooter.getParent()).removeView(mNoMoreDataFooter);
            }
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.loadMoreEnd();
                    mAdapter.addFooterView(mNoMoreDataFooter);
                }
            });
        }
    }

    @Override
    public void showDeletePinConfirmDialog(final String pinId, final int position) {
        DialogUtils.show(getActivity(), R.string.msg_delete_waring, R.string.msg_cancel,
                R.string.msg_confirm, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deletePin(pinId, position);
                    }
                });
    }

    @Override
    public void showEditPinDialog(EditPinDialogFragment editPinDialogFragment) {
        editPinDialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void showPinDeleted() {
        Observable.just(1)
                .delay(200, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        ((BoardActivity) getActivity()).onRefresh();
                    }
                });
    }

    @Override
    public void showLoginNav() {

    }

    public void onRefresh() {
        mAdapter.removeAllFooterView();
        mPresenter.getBoardPins(mBoardId);
    }
}
