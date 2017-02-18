package com.jess.arms.widget.imageloader.fresco;

import android.graphics.drawable.Drawable;

import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.jess.arms.widget.imageloader.ImageConfig;

/**
 * Created by Glooory on 17/2/16.
 * 配置 Fresco 加载图片的加载参数
 */

public class FrescoImageConfig extends ImageConfig {

    private SimpleDraweeView mSimpleDraweeView;
    private String mUrl;
    private String mUrlLow;//低分辨率图片的 url

    private Drawable mPlaceHolderImg;//占位图
    private Drawable mProgressbarImg;//loading 图
    private Drawable mRetryImg;//重试图
    private Drawable mFailureImg;//加载失败图
    private Drawable mBackgroundImg;//背景图

    private boolean mIsCircle;//是否为圆形
    private boolean mIsRadius;//是否有圆角
    private boolean mIsBorder;//是否包边
    private float mRadius = 5;//默认的圆角半径
    private ResizeOptions mResizeOptions;
    private ScalingUtils.ScaleType mScaleType = ScalingUtils.ScaleType.CENTER;

    private ControllerListener mControllerListener;//图片加载的回调
    private BaseBitmapDataSubscriber mBaseBitmapDataSubscriber;
    /**
     * 缓存策略
     * 0 对应 DiskCacheStrategy.ALL
     * 1 对应 DiskCacheStrategy.NONE
     * 2 对应 DiskCacheStrategy.SOURCE
     * 3 对应 DiskCacheStrategy.RESULT
     */
    private int mCacheStrategy = 1;//默认不缓存
    private Builder mBuilder;

    private FrescoImageConfig(Builder builder) {
        this.mSimpleDraweeView = builder.mSimpleDraweeView;
        this.mUrl = builder.mUrl;
        this.mUrlLow = builder.mUrlLow;
        this.mPlaceHolderImg = builder.mPlaceHolderImg;
        this.mProgressbarImg = builder.mProgressbarImg;
        this.mRetryImg = builder.mRetryImg;
        this.mFailureImg = builder.mFailureImg;
        this.mBackgroundImg = builder.mBackgroundImg;
        this.mIsCircle = builder.mIsCircle;
        this.mIsRadius = builder.mIsRadius;
        this.mIsBorder = builder.mIsBorder;
        this.mRadius = builder.mRadius;
        this.mResizeOptions = builder.mResizeOptions;
        this.mScaleType = builder.mScaleType;
        this.mControllerListener = builder.mControllerListener;
        this.mBaseBitmapDataSubscriber = builder.mBaseBitmapDataSubscriber;
        this.mCacheStrategy = builder.mCacheStrategy;
        this.mBuilder = builder;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getCacheStrategy() {
        return mCacheStrategy;
    }

    public SimpleDraweeView getSimpleDraweeView() {
        return mSimpleDraweeView;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    public String getUrlLow() {
        return mUrlLow;
    }

    public Drawable getPlaceHolderImg() {
        return mPlaceHolderImg;
    }

    public Drawable getProgressbarImg() {
        return mProgressbarImg;
    }

    public Drawable getRetryImg() {
        return mRetryImg;
    }

    public Drawable getFailureImg() {
        return mFailureImg;
    }

    public Drawable getBackgroundImg() {
        return mBackgroundImg;
    }

    public boolean isCircle() {
        return mIsCircle;
    }

    public boolean isRadius() {
        return mIsRadius;
    }

    public boolean isBorder() {
        return mIsBorder;
    }

    public float getRadius() {
        return mRadius;
    }

    public ResizeOptions getResizeOptions() {
        return mResizeOptions;
    }

    public ScalingUtils.ScaleType getScaleType() {
        return mScaleType;
    }

    public ControllerListener getControllerListener() {
        return mControllerListener;
    }

    public BaseBitmapDataSubscriber getBaseBitmapDataSubscriber() {
        return mBaseBitmapDataSubscriber;
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public static final class Builder {
        private SimpleDraweeView mSimpleDraweeView;
        private String mUrl;
        private String mUrlLow;//低分辨率图片的 url

        private Drawable mPlaceHolderImg;//占位图
        private Drawable mProgressbarImg;//loading 图
        private Drawable mRetryImg;//重试图
        private Drawable mFailureImg;//加载失败图
        private Drawable mBackgroundImg;//背景图

        private boolean mIsCircle;//是否为圆形
        private boolean mIsRadius;//是否有圆角
        private boolean mIsBorder;//是否包边
        private float mRadius = 5;//默认的圆角半径
        private ResizeOptions mResizeOptions;
        private ScalingUtils.ScaleType mScaleType = ScalingUtils.ScaleType.CENTER;

        private ControllerListener mControllerListener;//图片加载的回调
        private BaseBitmapDataSubscriber mBaseBitmapDataSubscriber;
        private int mCacheStrategy = 1;//默认不缓存

        private Builder() {
        }

        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }

        public Builder setSimpleDraweeView(SimpleDraweeView simpleDraweeView) {
            mSimpleDraweeView = simpleDraweeView;
            return this;
        }

        public Builder setLowUrl(String urlLow) {
            mUrlLow = urlLow;
            return this;
        }

        public Builder setPlaceHolder(Drawable placeHolder) {
            mPlaceHolderImg = placeHolder;
            return this;
        }

        public Builder setProgressbar(Drawable progressbar) {
            mProgressbarImg = progressbar;
            return this;
        }

        public Builder setRetryImage(Drawable retryImage) {
            mRetryImg = retryImage;
            return this;
        }

        public Builder setErrorImage(Drawable errorImage) {
            mFailureImg = errorImage;
            return this;
        }

        public Builder setBackground(Drawable backgroundImage) {
            mBackgroundImg = backgroundImage;
            return this;
        }

        public Builder isCircle(boolean isCircle) {
            mIsCircle = isCircle;
            return this;
        }

        public Builder isBorder(boolean isBorder) {
            mIsBorder = isBorder;
            return this;
        }

        public Builder isRadius(boolean isRadius) {
            mIsRadius = isRadius;
            return this;
        }

        public Builder setRadius(float radius) {
            mRadius = radius;
            return this;
        }

        public Builder setResizeOptions(ResizeOptions resizeOptions) {
            mResizeOptions = resizeOptions;
            return this;
        }

        public Builder setScaleType(ScalingUtils.ScaleType scaleType) {
            mScaleType = scaleType;
            return this;
        }

        public Builder setControlListener(ControllerListener listener) {
            mControllerListener = listener;
            return this;
        }

        public Builder setBitmapDataSubscriber(BaseBitmapDataSubscriber subscriber) {
            mBaseBitmapDataSubscriber = subscriber;
            return this;
        }

        public Builder setCacheStrategy(int cacheStrategy) {
            mCacheStrategy = cacheStrategy;
            return this;
        }

        public FrescoImageConfig build() {
            if (mUrl == null) {
                throw new IllegalStateException("url is required!");
            }
            if (mSimpleDraweeView == null) {
                throw new IllegalStateException("draweeview is required!");
            }
            if (mIsCircle && mIsRadius) {
                throw new IllegalArgumentException(
                        "you cannot set the image circle and radius at the same time!"
                );
            }
            return new FrescoImageConfig(this);
        }
    }
}
