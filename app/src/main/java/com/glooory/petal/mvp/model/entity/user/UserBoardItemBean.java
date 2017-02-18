package com.glooory.petal.mvp.model.entity.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.glooory.petal.mvp.model.entity.PinsBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/1 0001.
 */
public class UserBoardItemBean implements Parcelable {

    /**
     * board_id : 17793839
     * user_id : 15246080
     * title : 待归类采集
     * description : String
     * category_id : String
     * seq : 2
     * pin_count : 184
     * follow_count : 8
     * like_count : 0
     * created_at : 1411778585
     * updated_at : 1411778585
     * deleting : 0
     * is_private : 2
     * extra : {"cover":{"pin_id":"834279334"},"is_creation":true}
     * following : false
     */

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
    private int deleting = 1;
    private int is_private;
    private boolean following;

    private List<PinsBean> pins;
    private ExtraBean extra;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    private UserBean user;

    public void setPins(List<PinsBean> pins) {
        this.pins = pins;
    }

    public List<PinsBean> getPins() {
        return pins;
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

    public static class ExtraBean implements Parcelable {
        /**
         * pin_id : 834279334
         */

        private CoverBean cover;
        private boolean is_creation;

        public static ExtraBean objectFromData(String str) {

            return new Gson().fromJson(str, ExtraBean.class);
        }

        public CoverBean getCover() {
            return cover;
        }

        public void setCover(CoverBean cover) {
            this.cover = cover;
        }

        public boolean isIs_creation() {
            return is_creation;
        }

        public void setIs_creation(boolean is_creation) {
            this.is_creation = is_creation;
        }

        public static class CoverBean implements Parcelable {
            private String pin_id;

            public static CoverBean objectFromData(String str) {

                return new Gson().fromJson(str, CoverBean.class);
            }

            public String getPin_id() {
                return pin_id;
            }

            public void setPin_id(String pin_id) {
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

            public static final Parcelable.Creator<CoverBean> CREATOR = new Parcelable.Creator<CoverBean>() {
                @Override
                public CoverBean createFromParcel(Parcel source) {
                    return new CoverBean(source);
                }

                @Override
                public CoverBean[] newArray(int size) {
                    return new CoverBean[size];
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
            this.cover = in.readParcelable(CoverBean.class.getClassLoader());
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
        dest.writeByte(this.following ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.pins);
        dest.writeParcelable(this.extra, flags);
    }

    public UserBoardItemBean() {
    }

    protected UserBoardItemBean(Parcel in) {
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
        this.following = in.readByte() != 0;
        this.pins = in.createTypedArrayList(PinsBean.CREATOR);
        this.extra = in.readParcelable(ExtraBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserBoardItemBean> CREATOR = new Parcelable.Creator<UserBoardItemBean>() {
        @Override
        public UserBoardItemBean createFromParcel(Parcel source) {
            return new UserBoardItemBean(source);
        }

        @Override
        public UserBoardItemBean[] newArray(int size) {
            return new UserBoardItemBean[size];
        }
    };
}
