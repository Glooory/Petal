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
import com.glooory.petal.app.util.SPUtils;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import common.PEDialogFragment;

/**
 * Created by Gloooory on 17/3/20.
 */

public class CollectDialogFragment extends PEDialogFragment {

    private EditText mEditTextDes;
    private TextView mTvWarning;
    private Spinner mSpinner;

    private Context mContext;
    private int mPinId;
    private String mCollectDes;
    private boolean mIsCollected = false;
    private String mExistInBoardTitle;
    private String[] mBoardTitles;
    private String[] mBoardIds;
    private int mSelection = 0;
    private OnCollectActionListener mCollectActionListener;

    public static CollectDialogFragment create(int pinId, String des, boolean isCollected, String existIn) {
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
        initData();
    }

    private void initData() {
        String boardTitlesStr = (String) SPUtils.get(Constants.PREF_BOARD_TITLES, "");
        String boardIdsStr = (String) SPUtils.get(Constants.PREF_BOARD_IDS, "");

        String lastSaveBoard = (String) SPUtils.get(Constants.PREF_LAST_SAVE_BOARD, "");
        if (!TextUtils.isEmpty(boardTitlesStr) && !TextUtils.isEmpty(boardIdsStr)) {
            mBoardTitles = boardTitlesStr.split(Constants.COMMA);
            mBoardIds = boardIdsStr.split(Constants.COMMA);
            for (int i = 0; i < mBoardTitles.length; i++) {
                if (lastSaveBoard.equals(mBoardTitles[i])) {
                    mSelection = i;
                }
            }
        } else {
            // TODO: 17/3/20 Http for board info
            mBoardTitles = new String[0];
            mBoardIds = new String[0];
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.collection);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View content = layoutInflater.inflate(R.layout.dialog_collect, null);
        initContentView(content);
        builder.setView(content);

        builder.setNegativeButton(R.string.msg_cancel, null);
        builder.setPositiveButton(R.string.collection, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPUtils.putByApply(Constants.PREF_LAST_SAVE_BOARD, mBoardTitles[mSelection]);
                if (mCollectActionListener != null) {
                    mCollectActionListener.onCollectButtonClick(mEditTextDes.getText().toString(),
                            mBoardIds[mSelection]);
                }
            }
        });

        return builder.create();
    }

    private void initContentView(View contentView) {
        mEditTextDes = ButterKnife.findById(contentView, R.id.edit_text_collect_dialog_des);
        mSpinner = ButterKnife.findById(contentView, R.id.spinner_collect_dialog);
        mTvWarning = ButterKnife.findById(contentView, R.id.text_view_collect_dialog_warning);

        Logger.d(mIsCollected);
        Logger.d(mExistInBoardTitle);
        if (mIsCollected && !TextUtils.isEmpty(mExistInBoardTitle)) {
            String warningInfo = String.format(
                    getString(R.string.msg_collection_exist_format), mExistInBoardTitle);
            mTvWarning.setText(warningInfo);
            mTvWarning.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(mCollectDes)) {
            mEditTextDes.setHint(R.string.msg_collect_des);
        } else {
            mEditTextDes.setText(mCollectDes);
        }

        final HighLightArrayAdapter spinnerAdapter = new HighLightArrayAdapter(mContext,
                R.layout.support_simple_spinner_dropdown_item, mBoardTitles);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setSelection(mSelection);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerAdapter.setSelection(position);
                mSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setCollectActionListener(OnCollectActionListener listener) {
        mCollectActionListener = listener;
    }

    interface OnCollectActionListener {

        void onCollectButtonClick(String collectDes, String boardId);
    }
}
