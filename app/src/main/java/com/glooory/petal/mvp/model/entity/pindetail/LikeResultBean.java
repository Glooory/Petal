package com.glooory.petal.mvp.model.entity.pindetail;

/**
 * Created by Glooory on 2016/9/12 0012 17:09.
 * 喜欢某张图片操作后的返回信息
 */
public class LikeResultBean {

    private LikeBean like;

    public LikeBean getLike() {
        return like;
    }

    public void setLike(LikeBean like) {
        this.like = like;
    }

    public static class LikeBean {

        /**
         * pin_id : 690448610
         * user_id : 15246080
         * seq : 52990507
         * liked_at : 1461060515
         */

        private int pin_id;
        private int user_id;
        private int seq;
        private int liked_at;

        public int getPinId() {
            return pin_id;
        }

        public void setPinId(int pinId) {
            this.pin_id = pinId;
        }

        public int getUserId() {
            return user_id;
        }

        public void setUserId(int userId) {
            this.user_id = userId;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getLikedAt() {
            return liked_at;
        }

        public void setLikedAt(int likedAt) {
            this.liked_at = likedAt;
        }
    }
}
