package com.glooory.petal.mvp.model.entity.type;

import com.glooory.petal.mvp.model.entity.UserBean;

/**
 * Created by Glooory on 17/2/25.
 */

public class PusersBean {

    private int promote_user_id;
    private int user_id;
    private String category;
    private int status;
    private String description;
    private int priority;
    private int updated_at;
    private UserBean user;

    public int getPromoteUserId() {
        return promote_user_id;
    }

    public void setPromoteUserId(int promoteUserId) {
        this.promote_user_id = promote_user_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = user_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updated_at = updated_at;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
