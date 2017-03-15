package com.glooory.petal.mvp.model.entity;

/**
 * Created by Glooory on 17/3/15.
 */

public class BasicUserInfoBean {

    private String userName;
    private String mAvatarKey;
    private String mPinCount;
    private String mBoardCount;
    private String mFollowingCount;
    private String mFollowerCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarKey() {
        return mAvatarKey;
    }

    public void setAvatarKey(String avatarKey) {
        mAvatarKey = avatarKey;
    }

    public String getPinCount() {
        return mPinCount;
    }

    public void setPinCount(String pinCount) {
        mPinCount = pinCount;
    }

    public String getBoardCount() {
        return mBoardCount;
    }

    public void setBoardCount(String boardCount) {
        mBoardCount = boardCount;
    }

    public String getFollowingCount() {
        return mFollowingCount;
    }

    public void setFollowingCount(String followingCount) {
        mFollowingCount = followingCount;
    }

    public String getFollowerCount() {
        return mFollowerCount;
    }

    public void setFollowerCount(String followerCount) {
        mFollowerCount = followerCount;
    }
}
