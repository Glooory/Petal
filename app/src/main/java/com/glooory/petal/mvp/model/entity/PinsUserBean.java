package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class PinsUserBean implements Parcelable {

    private int user_id;
    private String username;
    private String urlname;
    private int created_at;
    /**
     * id : 112197876
     * farm : farm1
     * bucket : hbimg
     * key : a8954d7a5ee26c34c5313caa542aa361e60bd50530407-9Lnxz5
     * type : image/jpeg
     * width : 862
     * height : 930
     * frames : 1
     */

    private AvatarBean avatar;
    private int pin_count;
    private int follower_count;
    private int boards_like_count;
    private int board_count;
    private int commodity_count;
    private int like_count;
    private int creations_count;
    private int following_count;
    private ProfileBean profile;
    /**
     * newbietask : 0
     * lr : 1462270759
     * invites : 0
     * share : 0
     */

    private StatusBean status;

    public static PinsUserBean objectFromData(String str) {

        return new Gson().fromJson(str, PinsUserBean.class);
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

    public int getBoards_like_count() {
        return boards_like_count;
    }

    public void setBoards_like_count(int boards_like_count) {
        this.boards_like_count = boards_like_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getCommodity_count() {
        return commodity_count;
    }

    public void setCommodity_count(int commodity_count) {
        this.commodity_count = commodity_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getCreations_count() {
        return creations_count;
    }

    public void setCreations_count(int creations_count) {
        this.creations_count = creations_count;
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

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class AvatarBean implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.farm);
            dest.writeString(this.bucket);
            dest.writeString(this.key);
            dest.writeString(this.type);
            dest.writeString(this.width);
            dest.writeString(this.height);
            dest.writeString(this.frames);
        }

        public AvatarBean() {
        }

        protected AvatarBean(Parcel in) {
            this.id = in.readInt();
            this.farm = in.readString();
            this.bucket = in.readString();
            this.key = in.readString();
            this.type = in.readString();
            this.width = in.readString();
            this.height = in.readString();
            this.frames = in.readString();
        }

        public static final Parcelable.Creator<AvatarBean> CREATOR = new Parcelable.Creator<AvatarBean>() {
            @Override
            public AvatarBean createFromParcel(Parcel source) {
                return new AvatarBean(source);
            }

            @Override
            public AvatarBean[] newArray(int size) {
                return new AvatarBean[size];
            }
        };
    }

    public static class ProfileBean implements Parcelable {

        public static ProfileBean objectFromData(String str) {

            return new Gson().fromJson(str, ProfileBean.class);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public ProfileBean() {
        }

        protected ProfileBean(Parcel in) {
        }

        public static final Parcelable.Creator<ProfileBean> CREATOR = new Parcelable.Creator<ProfileBean>() {
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
        dest.writeInt(this.pin_count);
        dest.writeInt(this.follower_count);
        dest.writeInt(this.boards_like_count);
        dest.writeInt(this.board_count);
        dest.writeInt(this.commodity_count);
        dest.writeInt(this.like_count);
        dest.writeInt(this.creations_count);
        dest.writeInt(this.following_count);
        dest.writeParcelable(this.profile, flags);
        dest.writeParcelable(this.status, flags);
    }

    public PinsUserBean() {
    }

    protected PinsUserBean(Parcel in) {
        this.user_id = in.readInt();
        this.username = in.readString();
        this.urlname = in.readString();
        this.created_at = in.readInt();
        this.avatar = in.readParcelable(AvatarBean.class.getClassLoader());
        this.pin_count = in.readInt();
        this.follower_count = in.readInt();
        this.boards_like_count = in.readInt();
        this.board_count = in.readInt();
        this.commodity_count = in.readInt();
        this.like_count = in.readInt();
        this.creations_count = in.readInt();
        this.following_count = in.readInt();
        this.profile = in.readParcelable(ProfileBean.class.getClassLoader());
        this.status = in.readParcelable(StatusBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<PinsUserBean> CREATOR = new Parcelable.Creator<PinsUserBean>() {
        @Override
        public PinsUserBean createFromParcel(Parcel source) {
            return new PinsUserBean(source);
        }

        @Override
        public PinsUserBean[] newArray(int size) {
            return new PinsUserBean[size];
        }
    };
}

