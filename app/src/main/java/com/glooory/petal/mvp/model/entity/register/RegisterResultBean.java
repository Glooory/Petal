package com.glooory.petal.mvp.model.entity.register;

/**
 * Created by Glooory on 17/4/18.
 */

public class RegisterResultBean {


    /**
     * err : 403
     * msg : 用户已存在
     * access_token : 60de5-a08-43-b2a-c613ac896
     * refresh_token : b4389-b10-e2d5-cd6540dca
     * token_type : beaer
     * expires_in : 86400
     * state :
     */

    private int err;
    private String msg;
    private String access_token;
    private String refresh_token;
    private String token_type;
    private int expires_in;
    private String state;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }

    public String getRefreshToken() {
        return refresh_token;
    }

    public void setRefreshToken(String refreshToken) {
        this.refresh_token = refreshToken;
    }

    public String getTokenType() {
        return token_type;
    }

    public void setTokenType(String tokenType) {
        this.token_type = tokenType;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public void setExpiresIn(int expiresIn) {
        this.expires_in = expiresIn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
