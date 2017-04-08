package com.glooory.petal.mvp.ui.pindetail;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.adapter.HighLightArrayAdapter;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;

import java.util.List;

import butterknife.ButterKnife;
import common.PetalApplication;
import common.BasePetalDialogFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Gloooory on 17/3/20.
 */

public class CollectDialogFragment extends BasePetalDialogFragment {

    private EditText mEditTextDes;
    private Spinner mSpinnerBoards;

    private Context mContext;
    private int mPinId;
    private String mCollectDes;
    private boolean mIsCollected = false;
    private String mExistInBoardTitle;
    private String[] mBoardTitles;
    private String[] mBoardIds;
    private int mSelection = 0;
    private OnPinCollectListener mOnPinCollectListener;
    private HighLightArrayAdapter mSpinnerAdapter;

    public static CollectDialogFragment newInstance(int pinId, String des, boolean isCollected, String existIn) {
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRA_PIN_ID, pinId);
        args.putString(Constants.EXTRA_COLLECT_DES, des);
        args.putBoolean(Constants.EXTRA_IS_COLLECTED, isCollected);
        args.putString(Constants.EXTRA_EXIST_IN, existIn);
        CollectDialogFragment collectDialogFragment = new CollectDialogFragment();
        collectDialogFragment.setArguments(args);
        return collectDialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPinId = getArguments().getInt(Constants.EXTRA_PIN_ID);
            mCollectDes = getArguments().getString(Constants.EXTRA_COLLECT_DES);
            mIsCollected = getArguments().getBoolean(Constants.EXTRA_IS_COLLECTED);
            mExistInBoardTitle = getArguments().getString(Constants.EXTRA_EXIST_IN);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View content = layoutInflater.inflate(R.layout.dialog_pin_detail_collect, null);
        initContentView(content);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(R.string.collection)
                .setView(content)
                .setNegativeButton(R.string.msg_cancel, null)
                .setPositiveButton(R.string.collection, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnPinCollectListener != null) {
                            SPUtils.putByApply(Constants.PREF_LAST_SAVE_BOARD, mBoardTitles[mSelection]);
                            mOnPinCollectListener.onPinCollect(mEditTextDes.getText().toString(),
                                    mBoardIds[mSelection]);
                        }
                    }
                });

        requestBoardsInfo();
        return builder.create();
    }

    private void initContentView(View contentView) {
        mEditTextDes = ButterKnife.findById(contentView, R.id.edit_text_collect_dialog_des);
        mSpinnerBoards = ButterKnife.findById(contentView, R.id.spinner_collect_dialog);
        TextView tvWarning = ButterKnife.findById(contentView, R.id.text_view_collect_dialog_warning);

        if (mIsCollected && !TextUtils.isEmpty(mExistInBoardTitle)) {
            String warningInfo = String.format(
                    getString(R.string.format_collection_exist), mExistInBoardTitle);
            tvWarning.setText(warningInfo);
            tvWarning.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mCollectDes)) {
            mEditTextDes.setHint(R.string.msg_collect_des);
        } else {
            mEditTextDes.setText(mCollectDes);
        }
    }

    /**
     * 请求用户的所有画板信息，失败则从本地数据读取
     */
    private void requestBoardsInfo() {
        Subscription s = PetalApplication.getContext().getAppComponent()
                .serviceManager()
                .getUserService()
                .requestLatestBoardInfo(Constants.HTTP_RECOMMEND_TAGS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LatestEditBoardsBean>() {
                    @Override
                    public void onNext(LatestEditBoardsBean latestEditBoardsBean) {
                        if (latestEditBoardsBean.getBoards() != null) {
                            initBoardListData(latestEditBoardsBean.getBoards());
                        } else {
                            getLocalBoardList();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getLocalBoardList();
                    }
                });
        addSubscription(s);
    }

    private void initBoardListData(List<BoardBean> boardList) {
        mBoardTitles = new String[boardList.size()];
        mBoardIds = new String[boardList.size()];
        for (int i = 0; i < boardList.size(); i++) {
            mBoardTitles[i] = boardList.get(i).getTitle();
            mBoardIds[i] = String.valueOf(boardList.get(i).getBoardId());
        }
        initSpinnerAdapter();
    }

    private void getLocalBoardList() {
        String boardTitlesStr = (String) SPUtils.get(Constants.PREF_BOARD_TITLES, "");
        String boardIdsStr = (String) SPUtils.get(Constants.PREF_BOARD_IDS, "");
        if (!TextUtils.isEmpty(boardTitlesStr) && !TextUtils.isEmpty(boardIdsStr)) {
            mBoardTitles = boardTitlesStr.split(Constants.COMMA);
            mBoardIds = boardIdsStr.split(Constants.COMMA);
        } else {
            mBoardTitles = new String[0];
            mBoardIds = new String[0];
        }
        initSpinnerAdapter();
    }

    private void initSpinnerAdapter() {
        if (!TextUtils.isEmpty(mExistInBoardTitle)) {
            for (int i = 0; i < mBoardIds.length; i++) {
                if (mExistInBoardTitle.equals(mBoardIds[i])) {
                    mSelection = i;
                }
            }
        }

        mSpinnerAdapter = new HighLightArrayAdapter(mContext,
                R.layout.support_simple_spinner_dropdown_item, mBoardTitles);
        mSpinnerBoards.setAdapter(mSpinnerAdapter);
        mSpinnerBoards.setSelection(mSelection);
        mSpinnerBoards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerAdapter.setSelection(position);
                mSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setOnPinCollectListener(OnPinCollectListener listener) {
        mOnPinCollectListener = listener;
    }

    public interface OnPinCollectListener {

        void onPinCollect(String collectDes, String boardId);
    }
}
