package com.glooory.petal.mvp.model.entity.user;

        import java.util.List;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public class UserBoardListBean {

    private List<UserBoardItemBean> boards;

    public List<UserBoardItemBean> getBoards() {
        return boards;
    }

    public void setBoards(List<UserBoardItemBean> boards) {
        this.boards = boards;
    }

}
