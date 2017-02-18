package com.glooory.petal.mvp.model.entity.user;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/15 0015 12:16.
 */
public class UserFollowingBean {


    /**
     * user_id : 18146320
     * username : ~赏~悦~
     * urlname : d4qwlvet9e
     * created_at : 1448711571
     * avatar : {"id":97965969,"farm":"farm1","bucket":"hbimg","key":"5f142617b16a4a916809ab3910360137d3c5d877df34e-vV9yR9","type":"image/jpeg","width":"1920","height":"1080","frames":"1"}
     * extra : null
     * seq : 162768480
     * pins : [{"pin_id":854436761,"user_id":18146320,"board_id":31801035,"file_id":114664907,"file":{"bucket":"hbimg","key":"eebf6334c82c4ace540d4e597f0ed01ecc4dcd9d9d4b3-ZXjMJm","type":"image/jpeg","height":"820","width":"810","frames":"1"},"media_type":0,"source":"blogcache.artron.net","link":"http://blogcache.artron.net/201505/6/829941_14309088933egp.jpg","raw_text":"829941_14309088933egp.jpg (810×820)","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1473842216,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null},{"pin_id":854436426,"user_id":18146320,"board_id":31801035,"file_id":114664876,"file":{"bucket":"hbimg","key":"fd45d7c88072beea6ded8dbb2d8dc6cee751cbe4a0147-Ieyjz8","type":"image/jpeg","height":"864","width":"810","frames":"1"},"media_type":0,"source":"blogcache1.artron.net","link":"http://blogcache1.artron.net/201505/6/829941_14309088949UbB.jpg","raw_text":"829941_14309088949UbB.jpg (810×864)","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1473842205,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null},{"pin_id":854436007,"user_id":18146320,"board_id":31801035,"file_id":114664849,"file":{"bucket":"hbimg","key":"33f5598488d2c32d5fd8b6c93aa9b452e5862647f27af-g9r5PU","type":"image/jpeg","height":"1440","width":"810","frames":"1"},"media_type":0,"source":"blogcache.artron.net","link":"http://blogcache.artron.net/201507/26/829941_1437886228vBuU.jpg","raw_text":"829941_1437886228vBuU.jpg (810×1440)","text_meta":{},"via":2,"via_user_id":0,"original":null,"created_at":1473842191,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null}]
     * board_count : 2835
     * pin_count : 159531
     * like_count : 21
     * boards_like_count : 1
     * follower_count : 4600
     * commodity_count : 0
     * creations_count : 0
     * explore_following_count : 0
     * following_count : 950
     * profile : {"location":"","sex":"0","birthday":"","job":"","url":"","about":"采...采...采...喜欢就好！"}
     */

    private List<UsersBean> users;

    public static UserFollowingBean objectFromData(String str) {

        return new Gson().fromJson(str, UserFollowingBean.class);
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        private int user_id;
        private String username;
        private String urlname;
        private int created_at;
        /**
         * id : 97965969
         * farm : farm1
         * bucket : hbimg
         * key : 5f142617b16a4a916809ab3910360137d3c5d877df34e-vV9yR9
         * type : image/jpeg
         * width : 1920
         * height : 1080
         * frames : 1
         */

        private AvatarBean avatar;
        private Object extra;
        private int seq;
        private int board_count;
        private int pin_count;
        private int like_count;
        private int boards_like_count;
        private int follower_count;
        private int commodity_count;
        private int creations_count;
        private int explore_following_count;
        private int following_count;
        /**
         * location :
         * sex : 0
         * birthday :
         * job :
         * url :
         * about : 采...采...采...喜欢就好！
         */

        private ProfileBean profile;
        /**
         * pin_id : 854436761
         * user_id : 18146320
         * board_id : 31801035
         * file_id : 114664907
         * file : {"bucket":"hbimg","key":"eebf6334c82c4ace540d4e597f0ed01ecc4dcd9d9d4b3-ZXjMJm","type":"image/jpeg","height":"820","width":"810","frames":"1"}
         * media_type : 0
         * source : blogcache.artron.net
         * link : http://blogcache.artron.net/201505/6/829941_14309088933egp.jpg
         * raw_text : 829941_14309088933egp.jpg (810×820)
         * text_meta : {}
         * via : 2
         * via_user_id : 0
         * original : null
         * created_at : 1473842216
         * like_count : 0
         * comment_count : 0
         * repin_count : 0
         * is_private : 0
         * orig_source : null
         */

        private List<PinsBean> pins;

        public static UsersBean objectFromData(String str) {

            return new Gson().fromJson(str, UsersBean.class);
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

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
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

        public int getCommodity_count() {
            return commodity_count;
        }

        public void setCommodity_count(int commodity_count) {
            this.commodity_count = commodity_count;
        }

        public int getCreations_count() {
            return creations_count;
        }

        public void setCreations_count(int creations_count) {
            this.creations_count = creations_count;
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

        public ProfileBean getProfile() {
            return profile;
        }

        public void setProfile(ProfileBean profile) {
            this.profile = profile;
        }

        public List<PinsBean> getPins() {
            return pins;
        }

        public void setPins(List<PinsBean> pins) {
            this.pins = pins;
        }

        public static class AvatarBean {
            private int id;
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private String width;
            private String height;
            private String frames;

            public static AvatarBean objectFromData(String str) {

                return new Gson().fromJson(str, AvatarBean.class);
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFarm() {
                return farm;
            }

            public void setFarm(String farm) {
                this.farm = farm;
            }

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getFrames() {
                return frames;
            }

            public void setFrames(String frames) {
                this.frames = frames;
            }
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

        public static class PinsBean {
            private int pin_id;
            private int user_id;
            private int board_id;
            private int file_id;
            private AvatarBean file;
            private int media_type;
            private String source;
            private String link;
            private String raw_text;
            private int via;
            private int via_user_id;
            private Object original;
            private int created_at;
            private int like_count;
            private int comment_count;
            private int repin_count;
            private int is_private;
            private Object orig_source;

            public static PinsBean objectFromData(String str) {

                return new Gson().fromJson(str, PinsBean.class);
            }

            public int getPin_id() {
                return pin_id;
            }

            public void setPin_id(int pin_id) {
                this.pin_id = pin_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getBoard_id() {
                return board_id;
            }

            public void setBoard_id(int board_id) {
                this.board_id = board_id;
            }

            public int getFile_id() {
                return file_id;
            }

            public void setFile_id(int file_id) {
                this.file_id = file_id;
            }

            public AvatarBean getFile() {
                return file;
            }

            public void setFile(AvatarBean file) {
                this.file = file;
            }

            public int getMedia_type() {
                return media_type;
            }

            public void setMedia_type(int media_type) {
                this.media_type = media_type;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getRaw_text() {
                return raw_text;
            }

            public void setRaw_text(String raw_text) {
                this.raw_text = raw_text;
            }

            public int getVia() {
                return via;
            }

            public void setVia(int via) {
                this.via = via;
            }

            public int getVia_user_id() {
                return via_user_id;
            }

            public void setVia_user_id(int via_user_id) {
                this.via_user_id = via_user_id;
            }

            public Object getOriginal() {
                return original;
            }

            public void setOriginal(Object original) {
                this.original = original;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getRepin_count() {
                return repin_count;
            }

            public void setRepin_count(int repin_count) {
                this.repin_count = repin_count;
            }

            public int getIs_private() {
                return is_private;
            }

            public void setIs_private(int is_private) {
                this.is_private = is_private;
            }

            public Object getOrig_source() {
                return orig_source;
            }

            public void setOrig_source(Object orig_source) {
                this.orig_source = orig_source;
            }
        }
    }
}
