package com.glooory.petal.mvp.ui.category.board;

import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.app.Constants;
import com.glooory.petal.app.util.SPUtils;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import common.BasePetalAdapter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/4/1.
 */

public class CategoryBoardAdapter extends BasePetalAdapter<BoardBean, BaseViewHolder> {

    private String mCollectCountFormat;
    private String mFollowerCountFormat;
    private String mFollowStr;
    private String mFollowedStr;

    @Inject
    public CategoryBoardAdapter() {
        super(R.layout.item_cardview_category_board, null);
        Resources resources = PetalApplication.getContext().getResources();
        mCollectCountFormat = resources.getString(R.string.format_collection_count);
        mFollowerCountFormat = resources.getString(R.string.format_following_count);
        mFollowStr = resources.getString(R.string.nav_title_following);
        mFollowedStr = resources.getString(R.string.followed);
    }

    @Override
    protected void convert(BaseViewHolder holder, BoardBean boardBean) {
        boolean isFollowing = boardBean.isFollowing();
        boolean isMine = (String.valueOf(SPUtils.get(Constants.PREF_USER_ID, 0))
                .equals(String.valueOf(boardBean.getUserId())));

        String operateText;
        int operateDrawablResId;
        if (isMine) {
            operateText = "";
            operateDrawablResId = R.drawable.ic_block_grey_500_18dp;
        } else {
            if (!isFollowing) {
                operateText = mFollowStr;
                operateDrawablResId = R.drawable.ic_add_grey_500_18dp;
            } else {
                operateText = mFollowedStr;
                operateDrawablResId = R.drawable.ic_check_grey_500_18dp;
            }
        }

        ((TextView) holder.getView(R.id.text_view_user_board_operate))
                .setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(PetalApplication.getContext(), operateDrawablResId)
                        , null, null, null);

        holder.setText(R.id.text_view_user_board_title, boardBean.getTitle())
                .setText(R.id.text_view_user_board_collect_count,
                        String.format(mCollectCountFormat, boardBean.getPinCount()))
                .setText(R.id.text_view_user_board_follower_count,
                        String.format(mFollowerCountFormat, boardBean.getFollowCount()))
                .setText(R.id.text_view_category_board_user_name, boardBean.getUser().getUsername())
                .setText(R.id.text_view_user_board_operate, operateText)
                .addOnClickListener(R.id.ll_user_board_cover)
                .addOnClickListener(R.id.ll_category_board_user);

        if (!isMine) {
            holder.addOnClickListener(R.id.ll_user_board_operate);
        } else {
            holder.getView(R.id.ll_user_board_operate).setEnabled(false);
        }

        if (boardBean.getPins() == null) {
            return;
        }

        List<PinBean> pinList = boardBean.getPins();
        if (pinList == null) {
            pinList = new ArrayList<>(0);
        }

        SimpleDraweeView boardCover =
                (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_user_board_cover);
        SimpleDraweeView thumbnailFirst =
                (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_user_board_first);
        SimpleDraweeView thumbnailSecond =
                (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_user_board_second);
        SimpleDraweeView thumbnailThird =
                (SimpleDraweeView) holder.getView(R.id.simple_drawee_view_user_board_third);

        if (pinList.size() > 0) {
            mImageLoader.loadImage(PetalApplication.getContext(),
                    FrescoImageConfig.builder()
                            .setSimpleDraweeView(boardCover)
                            .setUrl(String.format(mGeneralImageUrlFormat, pinList.get(0).getFile().getKey()))
                            .setScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                            .build());
        } else {
            boardCover.setController(null);
        }

        if (pinList.size() > 1) {
            loadSmallBoardCover(thumbnailFirst, pinList.get(1).getFile().getKey());
        } else {
            thumbnailFirst.setController(null);
        }

        if (pinList.size() > 2) {
            loadSmallBoardCover(thumbnailSecond, pinList.get(2).getFile().getKey());
        } else {
            thumbnailSecond.setController(null);
        }

        if (pinList.size() > 3) {
            loadSmallBoardCover(thumbnailThird, pinList.get(3).getFile().getKey());
        } else {
            thumbnailThird.setController(null);
        }

        if (boardBean.getUser() != null) {
            loadUserAvatar((SimpleDraweeView) holder.getView(R.id.simple_drawee_view_category_board_user_avatar),
                    boardBean.getUser().getAvatar().getKey());
        } else {
            ((SimpleDraweeView) holder.getView(R.id.simple_drawee_view_category_board_user_avatar))
                    .setController(null);
        }
    }

    private void loadSmallBoardCover(SimpleDraweeView image, String imageUrlKey) {
        mImageLoader.loadImage(PetalApplication.getContext(),
                FrescoImageConfig.builder()
                        .setSimpleDraweeView(image)
                        .setUrl(String.format(mSmallImageUrlFormat, imageUrlKey))
                        .isRadius(true, 8)
                        .build());
    }

    private void loadUserAvatar(SimpleDraweeView image, String imageUrlKey) {
        mImageLoader.loadImage(PetalApplication.getContext(),
                FrescoImageConfig.builder()
                        .setSimpleDraweeView(image)
                        .setUrl(String.format(mSmallImageUrlFormat, imageUrlKey))
                        .isRadius(true, 4)
                        .build());
    }
}
