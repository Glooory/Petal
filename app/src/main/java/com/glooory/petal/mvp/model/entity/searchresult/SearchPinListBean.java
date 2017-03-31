package com.glooory.petal.mvp.model.entity.searchresult;

import com.glooory.petal.mvp.model.entity.PinBean;

import java.util.List;

/**
 * Created by Glooory on 17/3/31.
 */

public class SearchPinListBean {


    /**
     * query : {"text":"design","type":"pin","sort":"weight","page":1,"category":null}
     * pin_count : 724913
     * self_pin_count : 0
     * self_board_count : 0
     * board_count : 35751
     * people_count : 2481
     * facets : {}
     * pins : []
     * page : 1
     * category : null
     * target : /search/?q=design
     */

    private QueryBean query;
    private int pin_count;
    private int self_pin_count;
    private int self_board_count;
    private int board_count;
    private int people_count;
    private FacetsBean facets;
    private int page;
    private String target;
    private List<PinBean> pins;

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

    public FacetsBean getFacets() {
        return facets;
    }

    public void setFacets(FacetsBean facets) {
        this.facets = facets;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
