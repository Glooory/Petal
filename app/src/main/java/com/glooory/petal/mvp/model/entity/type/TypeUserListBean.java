package com.glooory.petal.mvp.model.entity.type;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/16 0016 21:15.
 * 请求分类数据时返回的相关用户实体类
 */
public class TypeUserListBean {

    private String filter;
    /**
     * promote_user_id : 5048
     * user_id : 206918
     * category : photography
     * status : 3
     * description :
     * priority : 0
     * updated_at : 1452151965
     */

    private List<PusersBean> pusers;

    public static TypeUserListBean objectFromData(String str) {

        return new Gson().fromJson(str, TypeUserListBean.class);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<PusersBean> getPusers() {
        return pusers;
    }

    public void setPusers(List<PusersBean> pusers) {
        this.pusers = pusers;
    }
}
