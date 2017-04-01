package com.glooory.petal.mvp.model.entity.searchresult;

import com.glooory.petal.mvp.model.entity.BoardBean;

import java.util.List;

/**
 * Created by Glooory on 17/4/1.
 */

public class SearchBoardListBean {


    /**
     * query : {"text":"fashion","type":"board","sort":"weight","page":1,"category":null}
     * pin_count : 186908
     * board_count : 6632
     * self_pin_count : 0
     * people_count : 336
     * facets : {}
     * boards : []
     * page : 1
     * category : null
     * target : /search/boards/?q=fashion
     */

    private QueryBean query;
    private int pin_count;
    private int board_count;
    private int self_pin_count;
    private int people_count;
    private FacetsBean facets;
    private int page;
    private Object category;
    private String target;
    private List<BoardBean> boards;

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

    public int getBoardCount() {
        return board_count;
    }

    public void setBoardCount(int boardCount) {
        this.board_count = boardCount;
    }

    public int getSelfPinCount() {
        return self_pin_count;
    }

    public void setSelfPinCount(int selfPinCount) {
        this.self_pin_count = selfPinCount;
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

    public List<BoardBean> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardBean> boards) {
        this.boards = boards;
    }
}
