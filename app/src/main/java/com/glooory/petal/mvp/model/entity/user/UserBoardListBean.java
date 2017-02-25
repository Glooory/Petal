package com.glooory.petal.mvp.model.entity.user;

import com.glooory.petal.mvp.model.entity.BoardBean;

import java.util.List;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public class UserBoardListBean {

    private List<BoardBean> boards;

    public List<BoardBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardBean> boards) {
        this.boards = boards;
    }
}
