package com.glooory.petal.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 17/2/24.
 */

public class FollowerBean {
    private String user_id;
    private String username;
    private String urlname;
    private int created_at;
    private AvatarBean avatar;
    private String extra;
    private int seq;
    private int follower_count;
    private int board_count;
    private int pin_count;
    private List<PinBean> pins;

    public static FollowerBean objectFromData(String str) {

        return new Gson().fromJson(str, FollowerBean.class);
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int createdAt) {
        this.created_at = createdAt;
    }

    public AvatarBean getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
        this.avatar = avatar;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getFollowerCount() {
        return follower_count;
    }

    public void setFollowerCount(int followerCount) {
        this.follower_count = followerCount;
    }

    public int getBoardCount() {
        return board_count;
    }

    public void setBoardCount(int boardCount) {
        this.board_count = boardCount;
    }

    public int getPinCount() {
        return pin_count;
    }

    public void setPinCount(int pinCount) {
        this.pin_count = pinCount;
    }

    public List<PinBean> getPins() {
        return pins;
    }

    public void setPins(List<PinBean> pins) {
        this.pins = pins;
    }
}
