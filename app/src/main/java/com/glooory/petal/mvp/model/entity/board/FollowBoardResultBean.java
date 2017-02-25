package com.glooory.petal.mvp.model.entity.board;

/**
 * Created by Glooory on 2016/9/14 0014 15:38.
 */
public class FollowBoardResultBean {

    private FollowBean follow;

    public FollowBean getFollow() {
        return follow;
    }

    public void setFollow(FollowBean follow) {
        this.follow = follow;
    }

    public static class FollowBean {

        /**
         * board_id : 967118
         * user_id : 15246080
         * board_owner_id : 134270
         * seq : 993083628
         * followed_at : 1462599833
         */

        private int board_id;
        private int user_id;
        private int board_owner_id;
        private int seq;
        private int followed_at;

        public int getBoardId() {
            return board_id;
        }

        public void setBoardId(int boardId) {
            this.board_id = board_id;
        }

        public int getUserId() {
            return user_id;
        }

        public void setUserId(int userId) {
            this.user_id = user_id;
        }

        public int getBoardOwnerId() {
            return board_owner_id;
        }

        public void setBoardOwnerId(int boardOwnerId) {
            this.board_owner_id = board_owner_id;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public int getFollowedAt() {
            return followed_at;
        }

        public void setFollowedAt(int followedAt) {
            this.followed_at = followed_at;
        }
    }
}
