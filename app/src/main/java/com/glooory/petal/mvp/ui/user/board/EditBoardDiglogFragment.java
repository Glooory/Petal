package com.glooory.petal.mvp.ui.user.board;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.glooory.petal.R;
import com.glooory.petal.app.adapter.HighLightArrayAdapter;

import butterknife.ButterKnife;
import common.PEApplication;

/**
 * Created by Glooory on 17/3/23.
 */

public class EditBoardDiglogFragment extends AppCompatDialogFragment {

    private static final String ARGS_BOARD_ID = "board_id";
    private static final String ARGS_BOARD_NAME = "board_name";
    private static final String ARGS_BOARD_DES = "board_des";
    private static final String ARGS_BOARD_CATEGORY = "board_category";

    private EditText mEditTextBoardName;
    private EditText mEditTextBoardDes;
    private Spinner mSpinnerCategory;

    private String mBoardIdOriginal;
    private String mBoardNameOriginal;
    private String mBoardDesOriginal;
    private String mBoardCategoryOriginal;
    private String[] mBoardCategories;
    private boolean mIsModified;
    private OnBoardEditListener mBoardEditListener;

    public static EditBoardDiglogFragment newInstance(String boardId, String boardName,
            String boardDes, String category) {
        Bundle args = new Bundle();
        args.putString(ARGS_BOARD_ID, boardId);
        args.putString(ARGS_BOARD_NAME, boardName);
        args.putString(ARGS_BOARD_DES, boardDes);
        args.putString(ARGS_BOARD_CATEGORY, category);
        EditBoardDiglogFragment fragment = new EditBoardDiglogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoardIdOriginal = getArguments().getString(ARGS_BOARD_ID);
            mBoardNameOriginal = getArguments().getString(ARGS_BOARD_NAME);
            mBoardDesOriginal = getArguments().getString(ARGS_BOARD_DES);
            mBoardCategoryOriginal = getArguments().getString(ARGS_BOARD_CATEGORY);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View diologContent = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_board_edit, null);
        initDialogView(diologContent);
        initData();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.edit)
                .setView(diologContent)
                .setNegativeButton(R.string.msg_cancel, null)
                .setNeutralButton(R.string.msg_delete_board, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mBoardEditListener != null) {
                            mBoardEditListener.onBoardDelete(mBoardIdOriginal);
                        }
                    }
                })
                .setPositiveButton(R.string.msg_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing here because we override this button later to change the close behaviour.
                        //However, we still need this because on older versions of Android unless we
                        //pass a handler the button doesn't get instantiated
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if (alertDialog != null) {
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isBoardNameEmpty()) {
                        mEditTextBoardName.setError(PEApplication.getContext()
                                .getString(R.string.msg_cannot_be_empty));
                        mEditTextBoardName.requestFocus();
                        return;
                    }
                    if (isModified()) {
                        if (mBoardEditListener != null) {
                            mBoardEditListener.onBoardEdit(mBoardIdOriginal,
                                    mEditTextBoardName.getText().toString(),
                                    mEditTextBoardDes.getText().toString(),
                                    mBoardCategoryOriginal);
                        }
                    }
                    alertDialog.dismiss();
                }
            });
        }
    }

    private void initDialogView(View diologContent) {
        mEditTextBoardName = ButterKnife.findById(diologContent, R.id.edit_text_dialog_board_edit_name);
        mEditTextBoardDes = ButterKnife.findById(diologContent, R.id.edit_text_dialog_board_edit_des);
        mSpinnerCategory = ButterKnife.findById(diologContent, R.id.spinner_dialog_board_edit);
    }

    private void initData() {
        if (!TextUtils.isEmpty(mBoardNameOriginal)) {
            mEditTextBoardName.setText(mBoardNameOriginal);
        }

        if (!TextUtils.isEmpty(mBoardDesOriginal)) {
            mEditTextBoardDes.setText(mBoardDesOriginal);
        }

        mBoardCategories = getActivity().getResources().getStringArray(R.array.category_value);
        String[] categoriesNames = getActivity().getResources().getStringArray(R.array.category_names);
        int selection = 0;
        if (!TextUtils.isEmpty(mBoardCategoryOriginal)) {
            for (int i = 0; i < mBoardCategories.length; i++) {
                if (mBoardCategoryOriginal.equals(mBoardCategories[i])) {
                    selection = i;
                    break;
                }
            }
        }
        final HighLightArrayAdapter arrayAdapter = new HighLightArrayAdapter(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, categoriesNames);
        mSpinnerCategory.setAdapter(arrayAdapter);
        mSpinnerCategory.setSelection(selection);
        arrayAdapter.setSelection(selection);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter.setSelection(position);
                String selectedCategory = mBoardCategories[position];
                if (!selectedCategory.equals(mBoardCategories)) {
                    mBoardCategoryOriginal = mBoardCategories[position];
                    mIsModified = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isBoardNameEmpty() {
        if (TextUtils.isEmpty(mEditTextBoardName.getText().toString())) {
            return true;
        }
        return false;
    }

    private boolean isModified() {
        boolean isModified = mIsModified;
        if (isModified) {
            return true;
        }

        String input;
        input = mEditTextBoardName.getText().toString();
        if (!TextUtils.isEmpty(input) && (!input.equals(mBoardNameOriginal))) {
            return true;
        }

        input = mEditTextBoardDes.getText().toString();
        if (!TextUtils.isEmpty(input) && (!input.equals(mBoardDesOriginal))) {
            return true;
        }

        return false;
    }

    public void setBoardEditListener(OnBoardEditListener boardEditListener) {
        mBoardEditListener = boardEditListener;
    }

    public interface OnBoardEditListener {

        void onBoardEdit(String boardId, String boardName, String boardDes, String category);

        void onBoardDelete(String boardId);
    }
}
