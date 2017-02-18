package com.glooory.petal.mvp.model.entity.user;

import com.glooory.petal.mvp.model.entity.AvatarBean;
import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/9/3 0003.
 */
public class UserBean {


    /**
     * user_id : 19070438
     * username : Glooory
     * urlname : glooory
     * avatar : {"id":108748356,"farm":"farm1","bucket":"hbimg","key":"db66518948c9eb954e30a96ef0597e54e7b1c0e2b56d-S4mivE","type":"image/png","width":"190","height":"190","frames":"1"}
     * board_count : 3
     * pin_count : 204
     * like_count : 0
     * boards_like_count : 0
     * follower_count : 0
     * muse_board_count : 0
     * explore_following_count : 0
     * following_count : 51
     * commodity_count : 0
     * profile : {"location":"成都","sex":"1","birthday":"1993-6-26","job":"Android","url":"http://github.glooory.io","about":"there is nothing to say"}
     * bindings : {}
     */

    private int user_id;
    private String username;
    private String urlname;
    private AvatarBean avatar;
    private int board_count;
    private int pin_count;
    private int like_count;
    private int boards_like_count;
    private int follower_count;
    private int muse_board_count;
    private int explore_following_count;
    private int following_count;
    private int commodity_count;
    /**
     * location : 成都
     * sex : 1
     * birthday : 1993-6-26
     * job : Android
     * url : http://github.glooory.io
     * about : there is nothing to say
     */

    private ProfileBean profile;

    public static UserBean objectFromData(String str) {

        return new Gson().fromJson(str, UserBean.class);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public void setAvatarBean(AvatarBean avatarBean) {
        this.avatar = avatarBean;
    }

    public AvatarBean getAvatarBean() {
        return avatar;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getBoards_like_count() {
        return boards_like_count;
    }

    public void setBoards_like_count(int boards_like_count) {
        this.boards_like_count = boards_like_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getMuse_board_count() {
        return muse_board_count;
    }

    public void setMuse_board_count(int muse_board_count) {
        this.muse_board_count = muse_board_count;
    }

    public int getExplore_following_count() {
        return explore_following_count;
    }

    public void setExplore_following_count(int explore_following_count) {
        this.explore_following_count = explore_following_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getCommodity_count() {
        return commodity_count;
    }

    public void setCommodity_count(int commodity_count) {
        this.commodity_count = commodity_count;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public static class ProfileBean {
        private String location;
        private String sex;
        private String birthday;
        private String job;
        private String url;
        private String about;

        public static ProfileBean objectFromData(String str) {

            return new Gson().fromJson(str, ProfileBean.class);
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }
    }
}
