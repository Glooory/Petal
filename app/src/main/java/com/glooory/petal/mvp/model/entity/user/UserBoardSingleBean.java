package com.glooory.petal.mvp.model.entity.user;

import com.glooory.petal.mvp.model.entity.ErrorBaseBean;

/**
 * Created by Glooory on 2016/9/14 0014 19:40.
 */
public class UserBoardSingleBean extends ErrorBaseBean {

    private UserBoardItemBean board;

    public UserBoardItemBean getBoards() {
        return board;
    }

    public void setBoards(UserBoardItemBean board) {
        this.board = board;
    }

}
