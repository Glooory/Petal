package com.glooory.petal.mvp.ui.home;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.util.DrawableUtils;
import com.glooory.petal.app.util.ImageUtils;
import com.glooory.petal.app.util.StringUtils;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/2/22.
 */

public class HomePinAdapter extends BasePetalAdapter<PinBean, BaseViewHolder> {

    @Inject
    public HomePinAdapter() {
        super(R.layout.item_cardview_pin_home, null);
    }

    @Override
    protected void convert(BaseViewHolder holder, PinBean item) {

        // 是否需要显示 Gif icon
        if (ImageUtils.isGif(item.getFile().getType())) {
            holder.getView(R.id.imgbtn_gif).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.imgbtn_gif).setVisibility(View.GONE);
        }

        // 图片描述为空时则不显示相应的 TextView
        if (StringUtils.isEmptyString(item.getRawText())) {
            holder.getView(R.id.textview_pin_des).setVisibility(View.GONE);
        } else {
            holder.setText(R.id.textview_pin_des, item.getRawText());
        }

        // 添加采集和喜欢的 icon
        Drawable collectDrawable = DrawableUtils.getTintDrawable(PetalApplication.getContext(),
                R.drawable.ic_pin_12dp, R.color.grey_500);
        Drawable likeDrawable = DrawableUtils.getTintDrawable(PetalApplication.getContext(),
                R.drawable.ic_like_12dp, R.color.grey_500);
        ((TextView) holder.getView(R.id.textview_collection_count))
                .setCompoundDrawablesWithIntrinsicBounds(collectDrawable, null, null, null);
        ((TextView) holder.getView(R.id.textview_like_count))
                .setCompoundDrawablesWithIntrinsicBounds(likeDrawable, null, null, null);

        holder.setText(R.id.textview_collection_count, StringUtils.appenUnit(item.getRepinCount()))
                .setText(R.id.textview_like_count, StringUtils.appenUnit(item.getLikeCount()))
                .setText(R.id.textview_pin_collection_via, setViaDesTextStyled(item))
                .addOnClickListener(R.id.ll_pin_img)
                .addOnClickListener(R.id.ll_pin_via_info)
                .addOnLongClickListener(R.id.ll_pin_img);

        float aspectRatio = ImageUtils.getAspectRatio(item.getFile().getWidth(),
                item.getFile().getHeight());
        ((SimpleDraweeView) holder.getView(R.id.simple_drawee_view_pin)).setAspectRatio(aspectRatio);
        Drawable placeHolder = DrawableUtils.getColoredPlaceHolderDrawable(item);

        if (!TextUtils.isEmpty(item.getFile().getKey())) {
            // 采集图片地址
            String pinUrl = String.format(mGeneralImageUrlFormat, item.getFile().getKey());
            mImageLoader.loadImage(PetalApplication.getContext(),
                    FrescoImageConfig.builder()
                            .setSimpleDraweeView(
                                    (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_pin))
                            .setUrl(pinUrl)
                            .setPlaceHolder(placeHolder)
                            .build());
        } else {
            ((SimpleDraweeView) holder.getView(R.id.simple_drawee_view_pin)).setController(null);
        }

        if (!TextUtils.isEmpty(item.getUser().getAvatar().getKey())) {
            // 头像图片地址
            String avatarUrl = String.format(mSmallImageUrlFormat, item.getUser().getAvatar().getKey());
            mImageLoader.loadImage(PetalApplication.getContext(),
                    FrescoImageConfig.builder()
                            .setSimpleDraweeView(
                                    (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_pin_avatar)
                            )
                            .setUrl(avatarUrl)
                            .isRadius(true, 10)
                            .build());
        } else {
            ((SimpleDraweeView) holder.getView(R.id.simple_drawee_view_pin_avatar)).setController(null);
        }
    }

    // 将用户名和画板名称颜色加深
    private SpannableString setViaDesTextStyled(PinBean pinBean) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pinBean.getUser().getUsername())
                .append(" 采集到 ")
                .append(pinBean.getBoard().getTitle());
        int userNameLength = pinBean.getUser().getUsername().length();
        int boardTitleLength = pinBean.getBoard().getTitle().length();
        SpannableString spannableString = new SpannableString(stringBuilder.toString());
        spannableString.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(PetalApplication.getContext(), R.color.primary_text)),
                0,
                userNameLength,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableString.setSpan(new ForegroundColorSpan(
                        ContextCompat.getColor(PetalApplication.getContext(), R.color.primary_text)),
                userNameLength + 5,
                userNameLength + 5 + boardTitleLength,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return spannableString;
    }
}
