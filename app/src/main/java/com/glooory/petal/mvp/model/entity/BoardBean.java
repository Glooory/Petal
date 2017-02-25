package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class BoardBean implements Parcelable {

    /*

      "board": {
        "board_id": 31489038,
        "user_id": 17368129,
        "title": "美女gif",
        "description": "",
        "category_id": "apparel",
        "seq": 9,
        "pin_count": 9,
        "follow_count": 23,
        "like_count": 0,
        "created_at": 1472264261,
        "updated_at": 1472264524,
        "deleting": 0,
        "is_private": 0,
        "extra": null
    }*/

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
    private UserBean user;
    private List<PinBean> pins;
    private boolean following;
    private ExtraBean extra;
    private List<String> recommend_tags;

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

    public String getCategoryId() {
        return category_id;
    }

    public void setCategoryId(String categoryId) {
        this.category_id = category_id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getPinCount() {
        return pin_count;
    }

    public void setPinCount(int pinCount) {
        this.pin_count = pin_count;
    }

    public int getFollowCount() {
        return follow_count;
    }

    public void setFollowCount(int followCount) {
        this.follow_count = follow_count;
    }

    public int getLikeCount() {
        return like_count;
    }

    public void setLikeCount(int likeCount) {
        this.like_count = like_count;
    }

    public int getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(int createdAt) {
        this.created_at = created_at;
    }

    public int getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updated_at = updated_at;
    }

    public int getDeleting() {
        return deleting;
    }

    public void setDeleting(int deleting) {
        this.deleting = deleting;
    }

    public int getIsPrivate() {
        return is_private;
    }

    public void setIsPrivate(int isPrivate) {
        this.is_private = is_private;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<PinBean> getPins() {
        return pins;
    }

    public void setPins(List<PinBean> pins) {
        this.pins = pins;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public List<String> getRecommendTags() {
        return recommend_tags;
    }

    public void setRecommendTags(List<String> recommendTags) {
        this.recommend_tags = recommend_tags;
    }

    public static class ExtraBean implements Parcelable {
        /**
         * pin_id : 834279334
         */

        private ExtraBean.CoverBean cover;
        private boolean is_creation;

        public static ExtraBean objectFromData(String str) {

            return new Gson().fromJson(str, ExtraBean.class);
        }

        public ExtraBean.CoverBean getCover() {
            return cover;
        }

        public void setCover(ExtraBean.CoverBean cover) {
            this.cover = cover;
        }

        public boolean isCreation() {
            return is_creation;
        }

        public void setIsCreation(boolean isCreation) {
            this.is_creation = is_creation;
        }

        public static class CoverBean implements Parcelable {
            private String pin_id;

            public static ExtraBean.CoverBean objectFromData(String str) {

                return new Gson().fromJson(str, ExtraBean.CoverBean.class);
            }

            public String getPinId() {
                return pin_id;
            }

            public void setPinId(String pinId) {
                this.pin_id = pin_id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.pin_id);
            }

            public CoverBean() {
            }

            protected CoverBean(Parcel in) {
                this.pin_id = in.readString();
            }

            public static final Parcelable.Creator<ExtraBean.CoverBean> CREATOR = new Parcelable.Creator<ExtraBean.CoverBean>() {
                @Override
                public ExtraBean.CoverBean createFromParcel(Parcel source) {
                    return new ExtraBean.CoverBean(source);
                }

                @Override
                public ExtraBean.CoverBean[] newArray(int size) {
                    return new ExtraBean.CoverBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.cover, flags);
            dest.writeByte(this.is_creation ? (byte) 1 : (byte) 0);
        }

        public ExtraBean() {
        }

        protected ExtraBean(Parcel in) {
            this.cover = in.readParcelable(ExtraBean.CoverBean.class.getClassLoader());
            this.is_creation = in.readByte() != 0;
        }

        public static final Parcelable.Creator<ExtraBean> CREATOR = new Parcelable.Creator<ExtraBean>() {
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
        dest.writeInt(this.board_id);
        dest.writeInt(this.user_id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.category_id);
        dest.writeInt(this.seq);
        dest.writeInt(this.pin_count);
        dest.writeInt(this.follow_count);
        dest.writeInt(this.like_count);
        dest.writeInt(this.created_at);
        dest.writeInt(this.updated_at);
        dest.writeInt(this.deleting);
        dest.writeInt(this.is_private);
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.pins);
        dest.writeByte(this.following ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.extra, flags);
        dest.writeStringList(this.recommend_tags);
    }

    public BoardBean() {
    }

    protected BoardBean(Parcel in) {
        this.board_id = in.readInt();
        this.user_id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.category_id = in.readString();
        this.seq = in.readInt();
        this.pin_count = in.readInt();
        this.follow_count = in.readInt();
        this.like_count = in.readInt();
        this.created_at = in.readInt();
        this.updated_at = in.readInt();
        this.deleting = in.readInt();
        this.is_private = in.readInt();
        this.user = in.readParcelable(UserBean.class.getClassLoader());
        this.pins = in.createTypedArrayList(PinBean.CREATOR);
        this.following = in.readByte() != 0;
        this.extra = in.readParcelable(ExtraBean.class.getClassLoader());
        this.recommend_tags = in.createStringArrayList();
    }

    public static final Creator<BoardBean> CREATOR = new Creator<BoardBean>() {
        @Override
        public BoardBean createFromParcel(Parcel source) {
            return new BoardBean(source);
        }

        @Override
        public BoardBean[] newArray(int size) {
            return new BoardBean[size];
        }
    };
}
