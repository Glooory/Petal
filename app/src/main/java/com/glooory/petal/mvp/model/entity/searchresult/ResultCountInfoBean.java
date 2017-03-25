package com.glooory.petal.mvp.model.entity.searchresult;

import com.glooory.petal.mvp.model.entity.PinBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/18 0018 12:07.
 * 搜索关键字的采集，画板，用户数量的实体类
 */
public class ResultCountInfoBean {

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

    private QueryBean query;
    private int pin_count;
    private int self_pin_count;
    private int self_board_count;
    private int board_count;
    private int people_count;
    private int page;
    private String category;
    private String target;
    private List<PinBean> pins;

    public static ResultCountInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, ResultCountInfoBean.class);
    }

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public int getPinCount() {
        return pin_count;
    }

    public void setPinCount(int pinCount) {
        this.pin_count = pinCount;
    }

    public int getSelfPinCount() {
        return self_pin_count;
    }

    public void setSelfPinCount(int selfPinCount) {
        this.self_pin_count = selfPinCount;
    }

    public int getSelfBoardCount() {
        return self_board_count;
    }

    public void setSelfBoardCount(int selfBoardCount) {
        this.self_board_count = selfBoardCount;
    }

    public int getBoardCount() {
        return board_count;
    }

    public void setBoardCount(int boardCount) {
        this.board_count = boardCount;
    }

    public int getPeopleCount() {
        return people_count;
    }

    public void setPeopleCount(int peopleCount) {
        this.people_count = peopleCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<PinBean> getPins() {
        return pins;
    }

    public void setPins(List<PinBean> pins) {
        this.pins = pins;
    }
}
