package com.glooory.petal.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/15 0015 17:18.
 */

public class PinListBean {

    private int followed_board;
    private List<PinBean> pins;

    public static PinListBean objectFromData(String str) {

        return new Gson().fromJson(str, PinListBean.class);
    }

    public int getFollowedBoard() {
        return followed_board;
    }

    public void setFollowedBoard(int followedBoard) {
        this.followed_board = followedBoard;
    }

    public List<PinBean> getPins() {
        return pins;
    }

    public void setPins(List<PinBean> pins) {
        this.pins = pins;
    }
}
