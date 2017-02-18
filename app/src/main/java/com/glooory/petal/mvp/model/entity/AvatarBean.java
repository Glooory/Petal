package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Glooory on 2016/8/29 0029.
 */
public class AvatarBean implements Parcelable {


    /**
     * id : 107389480
     * farm : farm1
     * bucket : hbimg
     * key : c903c32007d4c8e264607d99acc2195c25a430083ca2-CKTWbd
     * type : image/jpeg
     * height : 220
     * width : 334
     * frames : 1
     */

    private int id;
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private String height;
    private String width;
    private String frames;

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
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
        dest.writeString(this.height);
        dest.writeString(this.width);
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
        this.height = in.readString();
        this.width = in.readString();
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
