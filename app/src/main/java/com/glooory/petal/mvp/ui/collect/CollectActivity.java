package com.glooory.petal.mvp.ui.collect;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.adapter.HighLightArrayAdapter;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.di.component.DaggerCollectComponent;
import com.glooory.petal.di.module.CollectModule;
import com.glooory.petal.mvp.presenter.CollectPresenter;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import common.AppComponent;
import common.BasePetalActivity;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Glooory on 17/4/3.
 */

public class CollectActivity extends BasePetalActivity<CollectPresenter>
        implements CollectContract.View {

    public static final int CHOOSE_PICTURE_REQUEST_CODE = 423;

    @BindView(R.id.progressbar_uploading)
    ProgressBar mProgressbarUploading;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.simple_drawee_view_collect_preview)
    SimpleDraweeView mImgPreview;
    @BindView(R.id.spinner_collect)
    Spinner mSpinnerCollect;
    @BindView(R.id.edittext_collect_des)
    EditText mEdittextCollectDes;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_collect)
    Button mBtnCollect;

    private int mSelection;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, CollectActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCollectComponent.builder()
                .appComponent(appComponent)
                .collectModule(new CollectModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

        showCollectButton(false);
        RxView.clicks(mImgPreview)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                               @Override
                               public void call(Void aVoid) {
                                    mPresenter.choosePicture(CollectActivity.this);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });

        RxView.clicks(mBtnCancel)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                               @Override
                               public void call(Void aVoid) {
                                   finishSelf();
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });

        RxView.clicks(mBtnCollect)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .delay(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                               @Override
                               public void call(Void aVoid) {
                                   mProgressbarUploading.setVisibility(View.VISIBLE);
                                   mPresenter.collectImage(mSelection, mEdittextCollectDes.getText().toString());
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
    }

    @Override
    protected void initData() {
        mPresenter.getUserBoardsInfo();
        mPresenter.loadAddPictureIcon(mImgPreview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSE_PICTURE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mProgressbarUploading.setVisibility(View.VISIBLE);
                mPresenter.uploadPicture(mImgPreview, data);
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        SnackbarUtil.showLong(CollectActivity.this, message);
    }

    @Override
    public void hideUploadingProgressbar() {
        mProgressbarUploading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBoardSpinner(String[] boardNames, int lastEditedPosition) {
        final HighLightArrayAdapter highLightArrayAdapter = new HighLightArrayAdapter(
                this, R.layout.support_simple_spinner_dropdown_item, boardNames
        );
        highLightArrayAdapter.setSelection(lastEditedPosition);
        mSpinnerCollect.setAdapter(highLightArrayAdapter);
        mSpinnerCollect.setSelection(lastEditedPosition);
        mSpinnerCollect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                highLightArrayAdapter.setSelection(position);
                mSelection = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showCollectButton(boolean enabled) {
        mBtnCollect.setEnabled(enabled);
    }

    @Override
    public void showEmptyPreview() {
        mPresenter.loadAddPictureIcon(mImgPreview);
        mImgPreview.setBackgroundResource(R.drawable.bg_dash_line);
        mBtnCollect.setEnabled(false);
    }
}
