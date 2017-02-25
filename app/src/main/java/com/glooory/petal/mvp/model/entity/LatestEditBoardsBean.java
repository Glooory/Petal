package com.glooory.petal.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/25 0025 18:26.
 */

public class LatestEditBoardsBean {


    /**
     * board_id : 32101804
     * user_id : 19322933
     * title : rh
     * is_private : 0
     * category_id : null
     * recommend_tags : []
     * extra : null
     * pin_count : 1
     * follow_count : 0
     * description :
     */

    private List<BoardBean> boards;

    public static LatestEditBoardsBean objectFromData(String str) {

        return new Gson().fromJson(str, LatestEditBoardsBean.class);
    }

    public List<BoardBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardBean> boards) {
        this.boards = boards;
    }
}
