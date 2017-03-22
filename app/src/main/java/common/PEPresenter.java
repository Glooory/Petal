package common;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.glooory.petal.R;
import com.glooory.petal.app.util.SnackbarUtil;
import com.glooory.petal.mvp.ui.login.LoginActivity;
import com.jess.arms.base.BaseApplication;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.BaseView;
import com.jess.arms.mvp.IModel;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by Glooory on 17/3/18.
 */

public class PEPresenter<V extends BaseView, M extends IModel> extends BasePresenter<V, M> {

    private ImageLoader mImageLoader;
    private String mLargePicUrlFormat;
    private String mSmallPicUrlFormat;

    public PEPresenter(V rootView, M model) {
        super(rootView, model);
        mImageLoader = ((PEApplication) PEApplication.getContext()).getAppComponent().imageLoader();
        mLargePicUrlFormat = PEApplication.getContext().getString(R.string.url_image_large_format);
        mSmallPicUrlFormat = PEApplication.getContext().getString(R.string.url_image_small_format);
    }

    public void loadImage(String imageUrlKey, SimpleDraweeView image) {
        String imageUrl = String.format(mLargePicUrlFormat, imageUrlKey);
        Logger.d(imageUrl);
        Logger.d(image == null);
        mImageLoader.loadImage(((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                FrescoImageConfig.builder()
                        .setUrl(imageUrl)
                        .setSimpleDraweeView(image)
                        .setControlListener(new BaseControllerListener() {
                            @Override
                            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                                if (animatable != null) {
                                    animatable.start();
                                }
                            }
                        })
                        .build());
    }

    public void loadImage(String imageUrlKey, SimpleDraweeView image, Drawable placeHolder) {
        String imageUrl = String.format(mLargePicUrlFormat, imageUrlKey);
        mImageLoader.loadImage(((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                FrescoImageConfig.builder()
                        .setUrl(imageUrl)
                        .setSimpleDraweeView(image)
                        .setPlaceHolder(placeHolder)
                        .setControlListener(new BaseControllerListener() {
                            @Override
                            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                                if (animatable != null) {
                                    animatable.start();
                                }
                            }
                        })
                        .build());
    }

    public void loadImage(String imageUrlKey, SimpleDraweeView image, BaseBitmapDataSubscriber subscriber) {
        String imageUrl = String.format(mLargePicUrlFormat, imageUrlKey);
        mImageLoader.loadImage(((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                FrescoImageConfig.builder()
                        .setUrl(imageUrl)
                        .setSimpleDraweeView(image)
                        .setControlListener(new BaseControllerListener() {
                            @Override
                            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                                if (animatable != null) {
                                    animatable.start();
                                }
                            }
                        })
                        .setBitmapDataSubscriber(subscriber)
                        .build());
    }

    public void loadSmallImage(String imageUrlKey, SimpleDraweeView image) {
        String imageUrl = String.format(mSmallPicUrlFormat, imageUrlKey);
        mImageLoader.loadImage(((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                FrescoImageConfig.builder()
                        .setUrl(imageUrl)
                        .setSimpleDraweeView(image)
                        .build());
    }

    public void loadSmallCircleImage(String imageUrlKey, SimpleDraweeView image) {
        String imageUrl = String.format(mSmallPicUrlFormat, imageUrlKey);
        mImageLoader.loadImage(((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                FrescoImageConfig.builder()
                        .setUrl(imageUrl)
                        .setSimpleDraweeView(image)
                        .isCircle(true)
                        .build());
    }

    public void loadUserAvatar(String imageUrlKey, SimpleDraweeView image, BaseBitmapDataSubscriber subscriber) {
        String imageUrl = String.format(mSmallPicUrlFormat, imageUrlKey);
        mImageLoader.loadImage(((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                FrescoImageConfig.builder()
                        .setUrl(imageUrl)
                        .setSimpleDraweeView(image)
                        .isCircle(true)
                        .isBorder(true)
                        .setControlListener(new BaseControllerListener() {
                            @Override
                            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                                if (animatable != null) {
                                    animatable.start();
                                }
                            }
                        })
                        .setBitmapDataSubscriber(subscriber)
                        .build());
    }

    public void loadSmallRadiusImage(String imageUrlKey, SimpleDraweeView image, int radius) {
        String imageUrl = String.format(mSmallPicUrlFormat, imageUrlKey);
        mImageLoader.loadImage(((BaseApplication) PEApplication.getContext()).getAppManager().getCurrentActivity(),
                FrescoImageConfig.builder()
                        .setUrl(imageUrl)
                        .setSimpleDraweeView(image)
                        .isRadius(true, radius)
                        .build());
    }

    public void showLoginHintMsg() {
        SnackbarUtil.showLong(R.string.msg_login_hint, R.string.msg_go_login,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.launch(
                                ((BaseApplication) PEApplication.getContext())
                                        .getAppManager().getCurrentActivity(),
                                false);
                    }
                });
    }
}
