package com.glooory.petal.mvp.model.entity.type;

import com.glooory.petal.mvp.model.entity.AvatarBean;
import com.glooory.petal.mvp.model.entity.PinsBoardBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/16 0016 21:15.
 * 请求分类数据时返回的相关用户实体类
 */
public class TypeUserListBean {

    private String filter;
    /**
     * promote_user_id : 5048
     * user_id : 206918
     * category : photography
     * status : 3
     * description :
     * priority : 0
     * updated_at : 1452151965
     */

    private List<PusersBean> pusers;

    public static TypeUserListBean objectFromData(String str) {

        return new Gson().fromJson(str, TypeUserListBean.class);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<PusersBean> getPusers() {
        return pusers;
    }

    public void setPusers(List<PusersBean> pusers) {
        this.pusers = pusers;
    }

    public static class PusersBean {
        private int promote_user_id;
        private int user_id;
        private String category;
        private int status;
        private String description;
        private int priority;
        private int updated_at;
        /**
         * user_id : 206918
         * username : 浪子鱼
         * urlname : longlongfish
         * created_at : 1334291314
         * extra : null
         * board_count : 69
         * pin_count : 6064
         * follower_count : 17418
         * following : 0
         * profile : {"about":"猫不吃鱼...","location":"北京","url":""}
         */

        private UserBean user;

        public static PusersBean objectFromData(String str) {

            return new Gson().fromJson(str, PusersBean.class);
        }

        public int getPromote_user_id() {
            return promote_user_id;
        }

        public void setPromote_user_id(int promote_user_id) {
            this.promote_user_id = promote_user_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            /**
             * id : 101831222
             * farm : farm1
             * bucket : hbimg
             * key : d6b89a7714620fc4106c9300a8a6d851b0cd42cabf28e-U5Zzsl
             * type : image/jpeg
             * height : 1364
             * frames : 1
             * width : 1364
             */

            private AvatarBean avatar;
            private Object extra;
            private int board_count;
            private int pin_count;
            private int follower_count;
            private int following;
            /**
             * about : 猫不吃鱼...
             * location : 北京
             * url :
             */

            private ProfileBean profile;
            /**
             * lr : 1462259366
             * invites : 0
             * share : 0
             * past_shopping_welcome : 1
             * default_board : 2870043
             * hide_weibo : 1
             * hide_renren : 1
             * notificationsRead : []
             * past_shiji_guide : 1
             */

            private StatusBean status;

            private List<PinsBoardBean> boards;

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

            public int getFollower_count() {
                return follower_count;
            }

            public void setFollower_count(int follower_count) {
                this.follower_count = follower_count;
            }

            public int getFollowing() {
                return following;
            }

            public void setFollowing(int following) {
                this.following = following;
            }

            public ProfileBean getProfile() {
                return profile;
            }

            public void setProfile(ProfileBean profile) {
                this.profile = profile;
            }

            public StatusBean getStatus() {
                return status;
            }

            public void setStatus(StatusBean status) {
                this.status = status;
            }

            public List<PinsBoardBean> getBoards() {
                return boards;
            }

            public void setBoards(List<PinsBoardBean> boards) {
                this.boards = boards;
            }

            public static class ProfileBean {
                private String about;
                private String location;
                private String url;

                public static ProfileBean objectFromData(String str) {

                    return new Gson().fromJson(str, ProfileBean.class);
                }

                public String getAbout() {
                    return about;
                }

                public void setAbout(String about) {
                    this.about = about;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class StatusBean {
                private int lr;
                private int invites;
                private String share;
                private int past_shopping_welcome;
                private int default_board;
                private int hide_weibo;
                private int hide_renren;
                /**
                 * id : 1965665
                 * strike : img:http://hbfile.b0.upaiyun.com/img/notifications/201302/lianwu.jpg
                 */

                private NotifyBean notify;
                private int past_shiji_guide;
                private List<?> notificationsRead;

                public static StatusBean objectFromData(String str) {

                    return new Gson().fromJson(str, StatusBean.class);
                }

                public int getLr() {
                    return lr;
                }

                public void setLr(int lr) {
                    this.lr = lr;
                }

                public int getInvites() {
                    return invites;
                }

                public void setInvites(int invites) {
                    this.invites = invites;
                }

                public String getShare() {
                    return share;
                }

                public void setShare(String share) {
                    this.share = share;
                }

                public int getPast_shopping_welcome() {
                    return past_shopping_welcome;
                }

                public void setPast_shopping_welcome(int past_shopping_welcome) {
                    this.past_shopping_welcome = past_shopping_welcome;
                }

                public int getDefault_board() {
                    return default_board;
                }

                public void setDefault_board(int default_board) {
                    this.default_board = default_board;
                }

                public int getHide_weibo() {
                    return hide_weibo;
                }

                public void setHide_weibo(int hide_weibo) {
                    this.hide_weibo = hide_weibo;
                }

                public int getHide_renren() {
                    return hide_renren;
                }

                public void setHide_renren(int hide_renren) {
                    this.hide_renren = hide_renren;
                }

                public NotifyBean getNotify() {
                    return notify;
                }

                public void setNotify(NotifyBean notify) {
                    this.notify = notify;
                }

                public int getPast_shiji_guide() {
                    return past_shiji_guide;
                }

                public void setPast_shiji_guide(int past_shiji_guide) {
                    this.past_shiji_guide = past_shiji_guide;
                }

                public List<?> getNotificationsRead() {
                    return notificationsRead;
                }

                public void setNotificationsRead(List<?> notificationsRead) {
                    this.notificationsRead = notificationsRead;
                }

                public static class NotifyBean {
                    private int id;
                    private String strike;

                    public static NotifyBean objectFromData(String str) {

                        return new Gson().fromJson(str, NotifyBean.class);
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getStrike() {
                        return strike;
                    }

                    public void setStrike(String strike) {
                        this.strike = strike;
                    }
                }
            }

        }
    }
}
