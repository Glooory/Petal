package com.glooory.petal.mvp.model.entity.searchresult;

import com.glooory.petal.mvp.model.entity.PinsBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/18 0018 12:07.
 * 搜索关键字的采集，画板，用户数量的实体类
 */
public class ResultCountInfoBean {


    /**
     * text : coldplay
     * type : pin
     * sort : weight
     */

    private QueryBean query;
    /**
     * query : {"text":"coldplay","type":"pin","sort":"weight"}
     * pin_count : 777
     * self_pin_count : 0
     * self_board_count : 0
     * board_count : 29
     * people_count : 16
     * facets : {}
     * pins : []
     * page : 1
     * category : null
     * ads : {"fixedAds":[],"normalAds":[]}
     * target : /search/?q=coldplay
     */

    private int pin_count;
    private int self_pin_count;
    private int self_board_count;
    private int board_count;
    private int people_count;
    private int page;
    private Object category;
    private String target;
    private List<PinsBean> pins;

    public static ResultCountInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, ResultCountInfoBean.class);
    }

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getSelf_pin_count() {
        return self_pin_count;
    }

    public void setSelf_pin_count(int self_pin_count) {
        this.self_pin_count = self_pin_count;
    }

    public int getSelf_board_count() {
        return self_board_count;
    }

    public void setSelf_board_count(int self_board_count) {
        this.self_board_count = self_board_count;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getPeople_count() {
        return people_count;
    }

    public void setPeople_count(int people_count) {
        this.people_count = people_count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<PinsBean> getPins() {
        return pins;
    }

    public void setPins(List<PinsBean> pins) {
        this.pins = pins;
    }

    public static class QueryBean {
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
}
