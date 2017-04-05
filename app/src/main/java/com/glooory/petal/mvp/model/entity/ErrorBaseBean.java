package com.glooory.petal.mvp.model.entity;

/**
 * Created by Glooory on 2016/9/14 0014 19:39.
 */
public class ErrorBaseBean {

    /**
     * err : 403
     * msg : 相同标题的画板已经存在
     * board_id : 17891564
     */

    protected int err;
    protected String msg;

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

}
