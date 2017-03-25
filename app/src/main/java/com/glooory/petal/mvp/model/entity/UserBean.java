package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class UserBean implements Parcelable {

    private int user_id;
    private String username;
    private String urlname;
    private int created_at;
    private AvatarBean avatar;
    private String email;
    private int pin_count;
    private int follower_count;
    private int boards_like_count;
    private int board_count;
    private int commodity_count;
    private int like_count;
    private int creations_count;
    private int following_count;
    private ProfileBean profile;

    //该用户是否已经关注 关注为1 否则没有对应的字段
    private int following;
    private StatusBean status;

    private int muse_board_count;
    private int explore_following_count;
    private int seq;
    private List<PinBean> pins;
    private ExtraBean extra;
    private List<BoardBean> boards;

    public static UserBean objectFromData(String str) {

        return new Gson().fromJson(str, UserBean.class);
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
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

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int createdAt) {
        this.created_at = createdAt;
    }

    public AvatarBean getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPinCount() {
        return pin_count;
    }

    public void setPinCount(int pinCount) {
        this.pin_count = pinCount;
    }

    public int getFollowerCount() {
        return follower_count;
    }

    public void setFollowerCount(int followerCount) {
        this.follower_count = followerCount;
    }

    public int getBoardsLikeCount() {
        return boards_like_count;
    }

    public void setBoardsLikeCount(int boardsLikeCount) {
        this.boards_like_count = boardsLikeCount;
    }

    public int getBoardCount() {
        return board_count;
    }

    public void setBoardCount(int boardCount) {
        this.board_count = boardCount;
    }

    public int getCommodityCount() {
        return commodity_count;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodity_count = commodityCount;
    }

    public int getLikeCount() {
        return like_count;
    }

    public void setLikeCount(int likeCount) {
        this.like_count = likeCount;
    }

    public int getCreationsCount() {
        return creations_count;
    }

    public void setCreationsCount(int creationsCount) {
        this.creations_count = creationsCount;
    }

    public int getFollowingCount() {
        return following_count;
    }

    public void setFollowingCount(int followingCount) {
        this.following_count = followingCount;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public int getMuseBoardCount() {
        return muse_board_count;
    }

    public void setMuseBoardCount(int museBoardCount) {
        this.muse_board_count = museBoardCount;
    }

    public int getExploreFollowingCount() {
        return explore_following_count;
    }

    public void setExploreFollowingCount(int exploreFollowingCount) {
        this.explore_following_count = exploreFollowingCount;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public List<PinBean> getPins() {
        return pins;
    }

    public void setPins(List<PinBean> pins) {
        this.pins = pins;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public List<BoardBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardBean> boards) {
        this.boards = boards;
    }

    public static class ProfileBean implements Parcelable {
        private String location;
        private String sex;
        private String birthday;
        private String job;
        private String url;
        private String about;

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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.location);
            dest.writeString(this.sex);
            dest.writeString(this.birthday);
            dest.writeString(this.job);
            dest.writeString(this.url);
            dest.writeString(this.about);
        }

        public ProfileBean() {
        }

        protected ProfileBean(Parcel in) {
            this.location = in.readString();
            this.sex = in.readString();
            this.birthday = in.readString();
            this.job = in.readString();
            this.url = in.readString();
            this.about = in.readString();
        }

        public static final Creator<ProfileBean> CREATOR = new Creator<ProfileBean>() {
            @Override
            public ProfileBean createFromParcel(Parcel source) {
                return new ProfileBean(source);
            }

            @Override
            public ProfileBean[] newArray(int size) {
                return new ProfileBean[size];
            }
        };
    }

    public static class StatusBean implements Parcelable {
        private int newbietask;
        private int lr;
        private int invites;
        private String share;

        public static StatusBean objectFromData(String str) {

            return new Gson().fromJson(str, StatusBean.class);
        }

        public int getNewbietask() {
            return newbietask;
        }

        public void setNewbietask(int newbietask) {
            this.newbietask = newbietask;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.newbietask);
            dest.writeInt(this.lr);
            dest.writeInt(this.invites);
            dest.writeString(this.share);
        }

        public StatusBean() {
        }

        protected StatusBean(Parcel in) {
            this.newbietask = in.readInt();
            this.lr = in.readInt();
            this.invites = in.readInt();
            this.share = in.readString();
        }

        public static final Parcelable.Creator<StatusBean> CREATOR = new Parcelable.Creator<StatusBean>() {
            @Override
            public StatusBean createFromParcel(Parcel source) {
                return new StatusBean(source);
            }

            @Override
            public StatusBean[] newArray(int size) {
                return new StatusBean[size];
            }
        };
    }

    public static class ExtraBean implements Parcelable {
        private boolean is_museuser;

        public static ExtraBean objectFromData(String str) {

            return new Gson().fromJson(str, ExtraBean.class);
        }

        public boolean IsMuseuser() {
            return is_museuser;
        }

        public void setIsMuseuser(boolean isMuseuser) {
            this.is_museuser = isMuseuser;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.is_museuser ? (byte) 1 : (byte) 0);
        }

        public ExtraBean() {
        }

        protected ExtraBean(Parcel in) {
            this.is_museuser = in.readByte() != 0;
        }

        public static final Creator<ExtraBean> CREATOR = new Creator<ExtraBean>() {
            @Override
            public ExtraBean createFromParcel(Parcel source) {
                return new ExtraBean(source);
            }

            @Override
            public ExtraBean[] newArray(int size) {
                return new ExtraBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.user_id);
        dest.writeString(this.username);
        dest.writeString(this.urlname);
        dest.writeInt(this.created_at);
        dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.email);
        dest.writeInt(this.pin_count);
        dest.writeInt(this.follower_count);
        dest.writeInt(this.boards_like_count);
        dest.writeInt(this.board_count);
        dest.writeInt(this.commodity_count);
        dest.writeInt(this.like_count);
        dest.writeInt(this.creations_count);
        dest.writeInt(this.following_count);
        dest.writeParcelable(this.profile, flags);
        dest.writeInt(this.following);
        dest.writeParcelable(this.status, flags);
        dest.writeInt(this.muse_board_count);
        dest.writeInt(this.explore_following_count);
        dest.writeInt(this.seq);
        dest.writeTypedList(this.pins);
        dest.writeParcelable(this.extra, flags);
        dest.writeTypedList(this.boards);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this.user_id = in.readInt();
        this.username = in.readString();
        this.urlname = in.readString();
        this.created_at = in.readInt();
        this.avatar = in.readParcelable(AvatarBean.class.getClassLoader());
        this.email = in.readString();
        this.pin_count = in.readInt();
        this.follower_count = in.readInt();
        this.boards_like_count = in.readInt();
        this.board_count = in.readInt();
        this.commodity_count = in.readInt();
        this.like_count = in.readInt();
        this.creations_count = in.readInt();
        this.following_count = in.readInt();
        this.profile = in.readParcelable(ProfileBean.class.getClassLoader());
        this.following = in.readInt();
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
        this.muse_board_count = in.readInt();
        this.explore_following_count = in.readInt();
        this.seq = in.readInt();
        this.pins = in.createTypedArrayList(PinBean.CREATOR);
        this.extra = in.readParcelable(ExtraBean.class.getClassLoader());
        this.boards = in.createTypedArrayList(BoardBean.CREATOR);
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}

