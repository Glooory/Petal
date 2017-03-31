package com.glooory.petal.mvp.model.entity.searchresult;

/**
 * Created by Glooory on 17/3/31.
 */

public class QueryBean {
    /**
     * text : design
     * type : pin
     * sort : weight
     * page : 1
     * category : null
     */

    private String text;
    private String type;
    private String sort;
    private int page;

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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
