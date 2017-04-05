package com.glooory.petal.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.rx.BaseSubscriber;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.app.util.UIUtils;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.LatestEditBoardsBean;
import com.glooory.petal.mvp.model.entity.collect.CollectResultBean;
import com.glooory.petal.mvp.model.entity.collect.UploadResultBean;
import com.glooory.petal.mvp.ui.collect.CollectActivity;
import com.glooory.petal.mvp.ui.collect.CollectContract;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import common.BasePetalPresenter;
import common.PetalApplication;
import rx.functions.Action0;

/**
 * Created by Glooory on 17/4/3.
 */
@ActivityScope
public class CollectPresenter extends BasePetalPresenter<CollectContract.View, CollectContract.Model> {

    private int mPinId;
    private String[] mBoardNames;
    private String[] mBoardIds;

    @Inject
    public CollectPresenter(CollectContract.View rootView, CollectContract.Model model) {
        super(rootView, model);
    }

    public void getUserBoardsInfo() {
        if (!mModel.isLogin()) {
            return;
        }
        mModel.getUserLatestBoards()
                .compose(RxUtils.<LatestEditBoardsBean>bindToLifecycle(mRootView))
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
                        e.printStackTrace();
                    }
                });
    }

    private void initBoardListData(List<BoardBean> boardList) {
        int position = 0;
        String lastEditedBoard = (String) SPUtils.get(Constants.PREF_LAST_SAVE_BOARD, "");
        mBoardNames = new String[boardList.size()];
        mBoardIds = new String[boardList.size()];
        for (int i = 0; i < boardList.size(); i++) {
            mBoardNames[i] = boardList.get(i).getTitle();
            mBoardIds[i] = String.valueOf(boardList.get(i).getBoardId());
            if (lastEditedBoard.equals(mBoardNames[i])) {
                position = i;
            }
        }
        mRootView.showBoardSpinner(mBoardNames, position);
    }

    private void getLocalBoardList() {
        int position = 0;
        String lastEditedBoard = (String) SPUtils.get(Constants.PREF_LAST_SAVE_BOARD, "");
        String boardTitlesStr = (String) SPUtils.get(Constants.PREF_BOARD_TITLES, "");
        String boardIdsStr = (String) SPUtils.get(Constants.PREF_BOARD_IDS, "");
        if (!TextUtils.isEmpty(boardTitlesStr) && !TextUtils.isEmpty(boardIdsStr)) {
            mBoardNames = boardTitlesStr.split(Constants.COMMA);
            mBoardIds = boardIdsStr.split(Constants.COMMA);
        } else {
            mBoardNames = new String[0];
            mBoardIds = new String[0];
        }
        for (int i = 0; i < mBoardNames.length; i++) {
            if (lastEditedBoard.equals(mBoardNames[i])) {
                position = i;
            }
        }
        mRootView.showBoardSpinner(mBoardNames, position);
    }

    public void choosePicture(Activity activity) {
        if (!mModel.isLogin()) {
            LoginActivity.launch(activity, false);
            return;
        }
        Album.album(activity)
                .requestCode(CollectActivity.CHOOSE_PICTURE_REQUEST_CODE) // 请求码，返回时 onActivityResult() 的第一个参数
                .toolBarColor(ContextCompat.getColor(activity, R.color.colorPrimary)) // toolbar 颜色
                .statusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark)) // 状态栏颜色
                .title(activity.getString(R.string.choose_picture)) // title
                .selectCount(1) //最多选择多少张图片
                .columnCount(3) // 相册展示列数，默认是 2
                .camera(true) // 是否允许选择拍照
                .start();
    }

    public void loadAddPictureIcon(SimpleDraweeView simpleDraweeView) {
        ((PetalApplication) PetalApplication.getContext()).getAppComponent().imageLoader()
                .loadImage(PetalApplication.getContext(),
                        FrescoImageConfig.builder()
                                .setUrl("res:///" + R.drawable.ic_add_grey_500_48dp)
                                .setSimpleDraweeView(simpleDraweeView)
                                .setScaleType(ScalingUtils.ScaleType.CENTER)
                                .build());
    }

    public void uploadPicture(SimpleDraweeView imgPreview, Intent data) {
        ArrayList<String> imagePathList = Album.parseResult(data);
        if (imagePathList.size() <= 0) {
            return;
        }
        String imagePath = imagePathList.get(0);
        loadPreview(imgPreview, imagePath);
        uploadImage(imagePath);
    }

    /**
     * 将要上传的图片加载到预览框中
     * @param imagePreview
     * @param imagePath
     */
    private void loadPreview(SimpleDraweeView imagePreview, String imagePath) {
        // 获取图片的长宽，并适当压缩图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        float aspectRatio = options.outWidth / (float) options.outHeight;
        int desiredWidth = UIUtils.getScreenWidth(PetalApplication.getContext());
        int desiredHeight = (int) (desiredWidth / aspectRatio);
        imagePreview.setBackground(null);
        ((PetalApplication) PetalApplication.getContext()).getAppComponent().imageLoader()
                .loadImage(PetalApplication.getContext(),
                        FrescoImageConfig.builder()
                                .setUrl("file://" + imagePath)
                                .setSimpleDraweeView(imagePreview)
                                .setScaleType(ScalingUtils.ScaleType.CENTER_INSIDE)
                                .setResizeOptions(new ResizeOptions(desiredWidth, desiredHeight))
                                .build());
    }

    /**
     * 上传选中的图片到服务器,成功后保存返回的 pinId
     * @param imagePath
     */
    private void uploadImage(String imagePath) {
        mModel.uploadImage(imagePath)
                .compose(RxUtils.<UploadResultBean>bindToLifecycle(mRootView))
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideUploadingProgressbar();
                    }
                })
                .subscribe(new BaseSubscriber<UploadResultBean>() {
                    @Override
                    public void onNext(UploadResultBean uploadResultBean) {
                        Logger.d(uploadResultBean);
                        if (TextUtils.isEmpty(uploadResultBean.getMsg())) {
                            mPinId = uploadResultBean.getId();
                            mRootView.showCollectButton(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        SnackbarUtil.showLong(R.string.msg_upload_failed);
                    }
                });
    }

    public void collectImage(final int selection, String des) {
        if (mPinId == 0) {
            return;
        }
        mModel.saveLatestEditedBoard(mBoardNames[selection]);
        mModel.collect(mBoardIds[selection], des, String.valueOf(mPinId))
                .compose(RxUtils.<CollectResultBean>bindToLifecycle(mRootView))
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideUploadingProgressbar();
                    }
                })
                .subscribe(new BaseSubscriber<CollectResultBean>() {
                    @Override
                    public void onNext(CollectResultBean collectResultBean) {
                        if (collectResultBean.getPin() != null) {
                            String collectSuccessFormat = PetalApplication.getContext()
                                    .getString(R.string.format_collect_success);
                            String successInfo = String.format(collectSuccessFormat, mBoardNames[selection]);
                            SnackbarUtil.showLong(successInfo);
                            mRootView.showEmptyPreview();
                        }
                    }
                });
    }
}
