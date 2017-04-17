package com.glooory.petal.mvp.model.entity.user;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.BaseErrorBean;

/**
 * Created by Glooory on 2016/9/14 0014 19:40.
 */
public class UserBoardSingleBean extends BaseErrorBean {

    private BoardBean board;

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }
}
