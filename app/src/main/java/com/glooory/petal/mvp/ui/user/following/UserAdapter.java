package com.glooory.petal.mvp.ui.user.following;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.util.StringUtils;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;

import java.util.List;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/3/25.
 */

public class UserAdapter extends BasePetalAdapter<UserBean, BaseViewHolder> {

    private String mBoardCountFormat;
    private String mPinCountFormat;
    private StringBuilder mStringBuilder;
    private Drawable mFollowedDrawable;
    private Drawable mFollowDrawable;
    private String mFollowStr;
    private String mFollowedStr;

    @Inject
    public UserAdapter() {
        super(R.layout.item_cardview_user, null);
        Resources resources = PetalApplication.getContext().getResources();
        mBoardCountFormat = resources.getString(R.string.format_board_count);
        mPinCountFormat = resources.getString(R.string.format_collection_count);
        mStringBuilder = new StringBuilder();
        mFollowDrawable = ContextCompat.getDrawable(PetalApplication.getContext(), R.drawable.ic_add_grey_500_18dp);
        mFollowedDrawable = ContextCompat.getDrawable(PetalApplication.getContext(), R.drawable.ic_check_grey_500_18dp);
        mFollowStr = resources.getString(R.string.following);
        mFollowedStr = resources.getString(R.string.followed);
    }

    @Override
    protected void convert(BaseViewHolder holder, UserBean userBean) {
        mStringBuilder.setLength(0);
        mStringBuilder.append(String.format(mBoardCountFormat, userBean.getBoardCount()))
                .append(" ")
                .append(String.format(mPinCountFormat, StringUtils.appenUnit(userBean.getPinCount())));
        holder.setText(R.id.text_view_card_user_name, userBean.getUsername())
                .setText(R.id.text_view_card_user_info, mStringBuilder.toString())
                .addOnClickListener(R.id.ll_card_user)
                .addOnClickListener(R.id.ll_card_user_operate);
        boolean isFollowed = userBean.getFollowing() == 1;
        if (isFollowed) {
            ((TextView) holder.getView(R.id.text_view_card_user_operate)).setText(mFollowedStr);
            ((TextView) holder.getView(R.id.text_view_card_user_operate))
                    .setCompoundDrawablesWithIntrinsicBounds(mFollowedDrawable, null, null, null);
        } else {
            ((TextView) holder.getView(R.id.text_view_card_user_operate)).setText(mFollowStr);
            ((TextView) holder.getView(R.id.text_view_card_user_operate))
                    .setCompoundDrawablesWithIntrinsicBounds(mFollowDrawable, null, null, null);
        }

        loadAvatarImage(userBean.getAvatar().getKey(),
                (SimpleDraweeView) holder.getView(R.id.simple_drawee_card_user_avatar));

        List<PinBean> thumbnailList = userBean.getPins();
        if (thumbnailList == null || thumbnailList.size() == 0) {
            return;
        }

        loadThumbnail(thumbnailList.get(0).getFile().getKey(),
                (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_card_user_thumbnail_first));
        if (thumbnailList.size() >= 2) {
            loadThumbnail(thumbnailList.get(1).getFile().getKey(),
                    (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_card_user_thumbnail_second));
        }
        if (thumbnailList.size() >= 3) {
            loadThumbnail(thumbnailList.get(2).getFile().getKey(),
                    (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_card_user_thumbnail_third));
        }
    }

    private void loadAvatarImage(String imageUrlKey, SimpleDraweeView image) {
        mImageLoader.loadImage(PetalApplication.getContext(),
                FrescoImageConfig.builder()
                        .setUrl(String.format(mSmallImageUrlFormat, imageUrlKey))
                        .setSimpleDraweeView(image)
                        .isBorder(true)
                        .isCircle(true)
                        .build());
    }

    private void loadThumbnail(String imageUrlKey, SimpleDraweeView image) {
        mImageLoader.loadImage(PetalApplication.getContext(),
                FrescoImageConfig.builder()
                        .setUrl(String.format(mSmallImageUrlFormat, imageUrlKey))
                        .setSimpleDraweeView(image)
                        .isRadius(true, 8)
                        .build());
    }
}
