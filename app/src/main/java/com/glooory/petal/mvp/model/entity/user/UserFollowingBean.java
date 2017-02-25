package com.glooory.petal.mvp.model.entity.user;

import com.glooory.petal.mvp.model.entity.UserBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/15 0015 12:16.
 */
public class UserFollowingBean {

    private List<UserBean> users;

    public static UserFollowingBean objectFromData(String str) {

        return new Gson().fromJson(str, UserFollowingBean.class);
    }

    public List<UserBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }
}
