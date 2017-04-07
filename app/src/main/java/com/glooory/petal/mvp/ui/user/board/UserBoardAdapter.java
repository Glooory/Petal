package com.glooory.petal.mvp.ui.user.board;

import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.petal.R;
import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.jess.arms.widget.imageloader.fresco.FrescoImageConfig;

import java.util.ArrayList;
import java.util.List;

import common.BasePetalAdapter;
import common.PetalApplication;

/**
 * Created by Glooory on 17/3/22.
 */

public class UserBoardAdapter extends BasePetalAdapter<BoardBean, BaseViewHolder> {

    private boolean mIsMe; // 是否是已登录用户自己
    private String mCollectCountFormat;
    private String mFollowerCountFormat;
    private String mEditStr;
    private String mFollowStr;
    private String mFollowedStr;

    public UserBoardAdapter(boolean isMe) {
        super(R.layout.item_cardview_user_board, null);
        Resources resources = PetalApplication.getContext().getResources();
        mIsMe = isMe;
        mCollectCountFormat = resources.getString(R.string.format_collection_count);
        mFollowerCountFormat = resources.getString(R.string.format_following_count);
        mEditStr = resources.getString(R.string.edit);
        mFollowStr = resources.getString(R.string.nav_title_following);
        mFollowedStr = resources.getString(R.string.followed);
    }

    @Override
    protected void convert(BaseViewHolder holder, BoardBean boardBean) {
        boolean isFollowing = boardBean.isFollowing();
        boolean canOperate = false;
        // 不为 0 的标志位才能操作
        if (boardBean.getDeleting() != 0) {
            canOperate = true;
        }

        String operateText;
        int operateDrawablResId;
        if (canOperate) {
            if (mIsMe) {
                operateText = mEditStr;
                operateDrawablResId = R.drawable.ic_edit_grey_500_18dp;
            } else {
                if (isFollowing) {
                    operateText = mFollowedStr;
                    operateDrawablResId = R.drawable.ic_check_grey_500_18dp;
                } else {
                    operateText = mFollowStr;
                    operateDrawablResId = R.drawable.ic_add_grey_500_18dp;
                }
            }
        } else {
            operateText = "";
            operateDrawablResId = R.drawable.ic_block_grey_500_18dp;
            holder.getView(R.id.ll_user_board_operate).setEnabled(false);
            holder.getView(R.id.text_view_user_board_operate).setEnabled(false);
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
                .setText(R.id.text_view_user_board_operate, operateText)
                .addOnClickListener(R.id.ll_user_board_cover);
        if (canOperate) {
            holder.addOnClickListener(R.id.ll_user_board_operate);
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
            loadBoardThumbnail(thumbnailFirst, pinList.get(1).getFile().getKey());
        } else {
            thumbnailFirst.setController(null);
        }

        if (pinList.size() > 2) {
            loadBoardThumbnail(thumbnailSecond, pinList.get(2).getFile().getKey());
        } else {
            thumbnailSecond.setController(null);
        }

        if (pinList.size() > 3) {
            loadBoardThumbnail(thumbnailThird, pinList.get(3).getFile().getKey());
        } else {
            thumbnailThird.setController(null);
        }
    }

    private void loadBoardThumbnail(SimpleDraweeView image, String imageUrlKey) {
        mImageLoader.loadImage(PetalApplication.getContext(),
                FrescoImageConfig.builder()
                        .setSimpleDraweeView(image)
                        .setUrl(String.format(mSmallImageUrlFormat, imageUrlKey))
                        .isRadius(true, 8)
                        .build());
    }
}
