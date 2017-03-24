package com.glooory.petal.mvp.ui.user.board;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
 * Created by Glooory on 17/3/24.
 */

public class CreateBoardDialogFragment extends AppCompatDialogFragment {

    private EditText mEditTextBoardName;
    private EditText mEditTextBoardDes;
    private Spinner mSpinnerCategory;

    private String mBoardCategory;
    private String[] mBoardCategories;
    private OnCreateBoardListener mOnCreateBoardListener;


    public static CreateBoardDialogFragment newInstance() {
        return new CreateBoardDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View diologContent = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_board_edit, null);
        initDialogView(diologContent);
        initData();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.msg_new_board)
                .setView(diologContent)
                .setNegativeButton(R.string.msg_cancel, null)
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
                    if (mOnCreateBoardListener != null) {
                        mOnCreateBoardListener.onBoardCreate(
                                mEditTextBoardName.getText().toString(),
                                mEditTextBoardDes.getText().toString(),
                                mBoardCategory);
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
        mBoardCategories = getActivity().getResources().getStringArray(R.array.category_value);
        mBoardCategory = mBoardCategories[0];
        String[] categoriesNames = getActivity().getResources().getStringArray(R.array.category_names);
        int selection = 0;
        final HighLightArrayAdapter arrayAdapter = new HighLightArrayAdapter(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, categoriesNames);
        mSpinnerCategory.setAdapter(arrayAdapter);
        mSpinnerCategory.setSelection(selection);
        arrayAdapter.setSelection(selection);
        mSpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter.setSelection(position);
                mBoardCategory = mBoardCategories[position];
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

    public void setOnCreateBoardListener(OnCreateBoardListener onCreateBoardListener) {
        mOnCreateBoardListener = onCreateBoardListener;
    }

    public interface OnCreateBoardListener {

        void onBoardCreate(String boardName, String boardDes, String boardType);
    }

}
