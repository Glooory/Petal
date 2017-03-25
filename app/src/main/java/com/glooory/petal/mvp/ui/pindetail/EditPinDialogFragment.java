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

import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.adapter.HighLightArrayAdapter;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;

import java.util.List;

import butterknife.ButterKnife;
import common.PEApplication;
import common.PEDialogFragment;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 17/3/21.
 */

public class EditPinDialogFragment extends PEDialogFragment {

    private EditText mEditTextCollectDes;
    private Spinner mSpinnerBoardTitles;

    private Context mContext;
    private String mPinId;
    private String mBoardId;
    private String mCollectDes;
    private String[] mBoardTitles;
    private String[] mBoardIds;
    private int mSelection = 0;
    private HighLightArrayAdapter mSpinnerAdapter;
    private OnPinEditListener mPinEditListener;

    public static EditPinDialogFragment newInstance(String pinId, String boardId, String des) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_PIN_ID, pinId);
        args.putString(Constants.EXTRA_BOARD_ID, boardId);
        args.putString(Constants.EXTRA_COLLECT_DES, des);
        EditPinDialogFragment dialogFragment = new EditPinDialogFragment();
        dialogFragment.setArguments(args);
        return dialogFragment;
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
            mPinId = getArguments().getString(Constants.EXTRA_PIN_ID);
            mBoardId = getArguments().getString(Constants.EXTRA_BOARD_ID);
            mCollectDes = getArguments().getString(Constants.EXTRA_COLLECT_DES);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_pin_detail_edit, null);
        initView(contentView);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(R.string.edit)
                .setView(contentView)
                .setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mPinEditListener != null) {
                            mPinEditListener.onPinDelete();
                        }
                    }
                })
                .setNegativeButton(R.string.msg_cancel, null)
                .setPositiveButton(R.string.msg_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mPinEditListener != null) {
                            SPUtils.putByApply(Constants.PREF_LAST_SAVE_BOARD, mBoardTitles[mSelection]);
                            mPinEditListener.onPinEdit(mBoardIds[mSelection], mBoardTitles[mSelection],
                                    mEditTextCollectDes.getText().toString());
                        }
                    }
                });

        requestBoardsInfo();
        return builder.create();
    }

    private void initView(View contentView) {
        mEditTextCollectDes = ButterKnife.findById(contentView, R.id.edit_text_eidt_pin_dialog_des);
        mEditTextCollectDes.setText(mCollectDes);
        mSpinnerBoardTitles = ButterKnife.findById(contentView, R.id.spinner_edit_pin_dialog_boards);
    }

    /**
     * 请求用户的所有画板信息，失败则从本地数据读取
     */
    private void requestBoardsInfo() {
        Subscription s = ((PEApplication) PEApplication.getContext()).getAppComponent()
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
        for (int i = 0; i < mBoardIds.length; i++) {
            if (mBoardId.equals(mBoardIds[i])) {
                mSelection = i;
            }
        }
        mSpinnerAdapter = new HighLightArrayAdapter(mContext,
                R.layout.support_simple_spinner_dropdown_item, mBoardTitles);
        mSpinnerBoardTitles.setAdapter(mSpinnerAdapter);
        mSpinnerBoardTitles.setSelection(mSelection);
        mSpinnerBoardTitles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void setPinEditListener(OnPinEditListener pinEditListener) {
        mPinEditListener = pinEditListener;
    }

    public interface OnPinEditListener {

        void onPinDelete();

        void onPinEdit(String boardId, String boardName, String des);
    }
}
