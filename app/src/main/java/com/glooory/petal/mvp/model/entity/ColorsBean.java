package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by Glooory on 17/2/22.
 */

public class ColorsBean implements Parcelable {

    private int color;
    private double ratio;

    public static ColorsBean objectFromData(String str) {

        return new Gson().fromJson(str, ColorsBean.class);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.color);
        dest.writeDouble(this.ratio);
    }

    public ColorsBean() {
    }

    protected ColorsBean(Parcel in) {
        this.color = in.readInt();
        this.ratio = in.readDouble();
    }

    public static final Parcelable.Creator<ColorsBean> CREATOR = new Parcelable.Creator<ColorsBean>() {
        @Override
        public ColorsBean createFromParcel(Parcel source) {
            return new ColorsBean(source);
        }

        @Override
        public ColorsBean[] newArray(int size) {
            return new ColorsBean[size];
        }
    };
}
