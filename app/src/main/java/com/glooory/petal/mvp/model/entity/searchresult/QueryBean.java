package com.glooory.petal.mvp.model.entity.searchresult;

import com.google.gson.Gson;

/**
 * Created by Glooory on 17/2/25.
 */

public class QueryBean {

    /**
     * text : coldplay
     * type : pin
     * sort : weight
     */

    private String text;
    private String type;
    private String sort;

    public static QueryBean objectFromData(String str) {

        return new Gson().fromJson(str, QueryBean.class);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
