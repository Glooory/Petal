package com.glooory.petal.mvp.ui.searchresult.user;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;

import java.util.List;

import common.BasePetalAdapter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/4/1.
 */

public class SearchUserAdapter extends BasePetalAdapter<UserBean, BaseViewHolder> {

    private Drawable mFollowedDrawable;
    private Drawable mFollowDrawable;
    private String mFollowStr;
    private String mFollowedStr;
    private String mFollowerCountFormat;
    private String mMyUserId;

    public SearchUserAdapter() {
        super(R.layout.item_cardview_user, null);
        Resources resources = PetalApplication.getContext().getResources();
        mFollowerCountFormat = resources.getString(R.string.format_follower_count);
        mFollowDrawable = ContextCompat.getDrawable(
                PetalApplication.getContext(), R.drawable.ic_add_grey_500_18dp);
        mFollowedDrawable = ContextCompat.getDrawable(
                PetalApplication.getContext(), R.drawable.ic_check_grey_500_18dp);
        mFollowStr = resources.getString(R.string.following);
        mFollowedStr = resources.getString(R.string.followed);
        mMyUserId = String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0));
    }

    @Override
    protected void convert(BaseViewHolder holder, UserBean userBean) {
        boolean isMe = mMyUserId.equals(String.valueOf(userBean.getUserId()));

        holder.setText(R.id.text_view_card_user_name, userBean.getUsername())
                .setText(R.id.text_view_card_user_info,
                        String.format(mFollowerCountFormat, userBean.getFollowerCount()))
                .addOnClickListener(R.id.ll_card_user);
        if (isMe) {
            holder.getView(R.id.ll_card_user_operate).setEnabled(false);
        } else {
            holder.addOnClickListener(R.id.ll_card_user_operate);
        }
        boolean isFollowed = userBean.getFollowing() == 1;
        if (isMe) {
            ((TextView) holder.getView(R.id.text_view_card_user_operate)).setText("");
            ((TextView) holder.getView(R.id.text_view_card_user_operate))
                    .setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(PetalApplication.getContext(),
                                    R.drawable.ic_block_grey_500_18dp), null, null, null);
        } else {
            if (isFollowed) {
                ((TextView) holder.getView(R.id.text_view_card_user_operate)).setText(mFollowedStr);
                ((TextView) holder.getView(R.id.text_view_card_user_operate))
                        .setCompoundDrawablesWithIntrinsicBounds(mFollowedDrawable, null, null, null);
            } else {
                ((TextView) holder.getView(R.id.text_view_card_user_operate)).setText(mFollowStr);
                ((TextView) holder.getView(R.id.text_view_card_user_operate))
                        .setCompoundDrawablesWithIntrinsicBounds(mFollowDrawable, null, null, null);
            }
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
