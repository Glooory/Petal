package com.glooory.petal.mvp.model.entity.board;

import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/9/7 0007 16:50.
 */
public class SimpleBoardInfoBean {

    /**
     * board_id : 25394109
     * user_id : 10718490
     * title : 设计素材-动物
     * description :
     * category_id : photography
     * seq : 30
     * pin_count : 131
     * follow_count : 31
     * like_count : 1
     * created_at : 1442816593
     * updated_at : 1473236346
     * deleting : 0
     * is_private : 0
     * extra : null
     * user : {"user_id":10718490,"username":"小院青城","urlname":"l403315127","created_at":1384825969,"avatar":{"id":112197876,"farm":"farm1","bucket":"hbimg","key":"a8954d7a5ee26c34c5313caa542aa361e60bd50530407-9Lnxz5","type":"image/jpeg","width":"862","height":"930","frames":"1"},"extra":null,"pin_count":14857,"follower_count":457,"boards_like_count":0,"board_count":67,"commodity_count":24,"like_count":10,"creations_count":0,"following_count":65,"profile":{},"status":{"newbietask":0,"lr":1462270759,"invites":0,"share":"0"}}
     */

    private BoardBean board;


    public static SimpleBoardInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, SimpleBoardInfoBean.class);
    }

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public static class BoardBean {
        private int board_id;
        private int user_id;
        private String title;
        private String description;
        private String category_id;
        private int seq;
        private int pin_count;
        private int follow_count;
        private int like_count;
        private int created_at;
        private int updated_at;
        private int deleting;
        private int is_private;
        private Object extra;
        private com.glooory.petal.mvp.model.entity.PinsUserBean user;


        public static BoardBean objectFromData(String str) {

            return new Gson().fromJson(str, BoardBean.class);
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
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

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public int getDeleting() {
            return deleting;
        }

        public void setDeleting(int deleting) {
            this.deleting = deleting;
        }

        public int getIs_private() {
            return is_private;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        public com.glooory.petal.mvp.model.entity.PinsUserBean getUser() {
            return user;
        }

        public void setUser(com.glooory.petal.mvp.model.entity.PinsUserBean user) {
            this.user = user;
        }
    }
}
