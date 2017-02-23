package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class PinsFileBean implements Parcelable {

    // Pins 中的File

    /*"file":{
        "id":112862047,
            "farm":"farm1",
            "bucket":"hbimg",
            "key":"fda7fcbb8efbc9177556f2f2989b0a2f5d6008f414951e-UT8MUO",
            "type":"image/gif",
            "height":"275",
            "width":"274",
            "frames":"52"
    }*/

    private int id;
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private int height;
    private int width;
    private String frames;
    private List<ColorsBean> colors;

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getFrames() {
        return frames;
    }

    public void setFrames(String frames) {
        this.frames = frames;
    }

    public List<ColorsBean> getColors() {
        return colors;
    }

    public void setColors(List<ColorsBean> colors) {
        this.colors = colors;
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
        dest.writeInt(this.height);
        dest.writeInt(this.width);
        dest.writeString(this.frames);
        dest.writeTypedList(this.colors);
    }

    public PinsFileBean() {
    }

    protected PinsFileBean(Parcel in) {
        this.id = in.readInt();
        this.farm = in.readString();
        this.bucket = in.readString();
        this.key = in.readString();
        this.type = in.readString();
        this.height = in.readInt();
        this.width = in.readInt();
        this.frames = in.readString();
        this.colors = in.createTypedArrayList(ColorsBean.CREATOR);
    }

    public static final Creator<PinsFileBean> CREATOR = new Creator<PinsFileBean>() {
        @Override
        public PinsFileBean createFromParcel(Parcel source) {
            return new PinsFileBean(source);
        }

        @Override
        public PinsFileBean[] newArray(int size) {
            return new PinsFileBean[size];
        }
    };
}
