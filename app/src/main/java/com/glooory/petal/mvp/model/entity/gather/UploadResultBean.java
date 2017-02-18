package com.glooory.petal.mvp.model.entity.gather;

import com.glooory.petal.mvp.model.entity.ErrorBaseBean;
import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/9/23 0023 16:46.
 * 本地图片上传成功后服务器返回的实体类
 */

public class UploadResultBean  extends ErrorBaseBean{


    /**
     * id : 86216689
     * farm : farm1
     * bucket : hbimg
     * key : 0dab012647abd037bd0d071aafe63893d615fffeb67f-XseJjw
     * type : image/jpeg
     * width : 428
     * height : 640
     * frames : 1
     */

    private int id;
    private String farm;
    private String bucket;
    private String key;
    private String type;
    private int width;
    private int height;
    private int frames;

    public static UploadResultBean objectFromData(String str) {

        return new Gson().fromJson(str, UploadResultBean.class);
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    @Override
    public String toString() {
        return "UploadResultBean{" +
                "id=" + id +
                ", farm='" + farm + '\'' +
                ", bucket='" + bucket + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", frames=" + frames +
                '}';
    }
}
