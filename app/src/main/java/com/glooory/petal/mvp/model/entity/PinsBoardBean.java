package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class PinsBoardBean implements Parcelable {

    /* pins[] json数组中的json数据

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
    private Object extra;
    private List<PinsBean> pins;

    public int getBoard_id() {
        return board_id;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
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

    public List<PinsBean> getPins() {
        return pins;
    }

    public void setPins(List<PinsBean> pins) {
        this.pins = pins;
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
        dest.writeTypedList(this.pins);
    }

    public PinsBoardBean() {
    }

    protected PinsBoardBean(Parcel in) {
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
        this.pins = in.createTypedArrayList(com.glooory.petal.mvp.model.entity.PinsBean.CREATOR);
    }

    public static final Parcelable.Creator<PinsBoardBean> CREATOR = new Parcelable.Creator<PinsBoardBean>() {
        @Override
        public PinsBoardBean createFromParcel(Parcel source) {
            return new PinsBoardBean(source);
        }

        @Override
        public PinsBoardBean[] newArray(int size) {
            return new PinsBoardBean[size];
        }
    };
}
