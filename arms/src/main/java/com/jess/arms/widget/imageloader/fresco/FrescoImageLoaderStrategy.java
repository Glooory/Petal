package com.jess.arms.widget.imageloader.fresco;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jess.arms.widget.imageloader.BaseImageLoaderStrategy;

import javax.inject.Inject;

/**
 * Created by Glooory on 17/2/17.
 */

public class FrescoImageLoaderStrategy implements BaseImageLoaderStrategy<FrescoImageConfig> {

    @Inject
    public FrescoImageLoaderStrategy() {
    }

    @Override
    public void loadImage(Context context, FrescoImageConfig config) {
        //初始化 M 层，用于初始化图片中包含的数据
        GenericDraweeHierarchyBuilder builderM =
                new GenericDraweeHierarchyBuilder(context.getResources());

        //请求参数，主要配置 url 和 C 层相关
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(config.getUrl()))
                .setProgressiveRenderingEnabled(true);//设置渐进式加载
        if (config.getResizeOptions() != null) {
            imageRequestBuilder.setResizeOptions(config.getResizeOptions());
        }

        ImageRequest imageRequest = imageRequestBuilder.build();

        //初始化 C 层，用于控制图片的加载
        PipelineDraweeControllerBuilder builderC = Fresco.newDraweeControllerBuilder();
        if (config.getUrlLow() != null) {
            builderC.setLowResImageRequest(ImageRequest.fromUri(Uri.parse(config.getUrlLow())));
        }

        builderC.setImageRequest(imageRequest);
        //配置渐进式加载
        builderC.setOldController(config.getSimpleDraweeView().getController());

        setViewPerformance(config, builderM, builderC);

        if (config.getControllerListener() != null) {
            builderC.setControllerListener(config.getControllerListener());
        }

        DraweeController draweeController = builderC.build();

        if (config.getBaseBitmapDataSubscriber() != null) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();

            DataSource<CloseableReference<CloseableImage>> dataSource =
                    imagePipeline.fetchDecodedImage(imageRequest, config.getSimpleDraweeView().getContext());
            dataSource.subscribe(config.getBaseBitmapDataSubscriber(), CallerThreadExecutor.getInstance());
        }

        config.getSimpleDraweeView().setHierarchy(builderM.build());
        config.getSimpleDraweeView().setController(draweeController);
    }

    /**
     * 配置 DraweeView 的各种加载效果
     * 如：圆角、圆形、占位图、重试图、失败图等
     * @param config
     * @param builderM
     * @param builderC
     */
    private void setViewPerformance(FrescoImageConfig config,
            GenericDraweeHierarchyBuilder builderM,
            PipelineDraweeControllerBuilder builderC) {
        builderM.setActualImageScaleType(config.getScaleType());
        if (config.getScaleType() == ScalingUtils.ScaleType.FOCUS_CROP) {
            builderM.setActualImageFocusPoint(new PointF(0F, 0F));
        }

        if (config.getPlaceHolderImg() != null) {
            builderM.setPlaceholderImage(config.getPlaceHolderImg(), ScalingUtils.ScaleType.CENTER);
        }

        if (config.getProgressbarImg() != null) {
            Drawable progressbarDrawable = new AutoRotateDrawable(config.getProgressbarImg(), 2000);
            builderM.setProgressBarImage(progressbarDrawable);
        }

        if (config.getRetryImg() != null) {
            builderC.setTapToRetryEnabled(true);
            builderM.setRetryImage(config.getRetryImg());
        }

        if (config.getFailureImg() != null) {
            builderM.setFailureImage(config.getFailureImg());
        }

        if (config.getBackgroundImg() != null) {
            builderM.setBackground(config.getBackgroundImg());
        }

        if (config.isCircle()) {
            if (config.isBorder()) {
                //默认白色包边
                builderM.setRoundingParams(RoundingParams.asCircle().setBorder(0xFFFFFFFF, 2));
            } else {
                builderM.setRoundingParams(RoundingParams.asCircle());
            }
        }

        if (config.isRadius()) {
            builderM.setRoundingParams(RoundingParams.fromCornersRadius(config.getRadius()));
        }
    }
}
