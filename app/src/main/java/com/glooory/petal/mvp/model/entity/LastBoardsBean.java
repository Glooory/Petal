package com.glooory.petal.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/25 0025 18:26.
 */

public class LastBoardsBean {


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

    private List<BoardsBean> boards;

    public static LastBoardsBean objectFromData(String str) {

        return new Gson().fromJson(str, LastBoardsBean.class);
    }

    public List<BoardsBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardsBean> boards) {
        this.boards = boards;
    }

    public static class BoardsBean {
        private int board_id;
        private int user_id;
        private String title;
        private int is_private;
        private Object category_id;
        private Object extra;
        private int pin_count;
        private int follow_count;
        private String description;
        private List<?> recommend_tags;

        public static BoardsBean objectFromData(String str) {

            return new Gson().fromJson(str, BoardsBean.class);
        }

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIs_private() {
            return is_private;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public Object getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Object category_id) {
            this.category_id = category_id;
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        public int getPin_count() {
            return pin_count;
        }

        public void setPin_count(int pin_count) {
            this.pin_count = pin_count;
        }

        public int getFollow_count() {
            return follow_count;
        }

        public void setFollow_count(int follow_count) {
            this.follow_count = follow_count;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<?> getRecommend_tags() {
            return recommend_tags;
        }

        public void setRecommend_tags(List<?> recommend_tags) {
            this.recommend_tags = recommend_tags;
        }
    }
}
