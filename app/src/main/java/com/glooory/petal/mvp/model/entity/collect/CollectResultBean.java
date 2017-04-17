package com.glooory.petal.mvp.model.entity.collect;

import com.glooory.petal.mvp.model.entity.BaseErrorBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/9/23 0023 20:40.
 */

public class CollectResultBean extends BaseErrorBean {

    private PinBean pin;

    public static CollectResultBean objectFromData(String str) {

        return new Gson().fromJson(str, CollectResultBean.class);
    }

    public PinBean getPin() {
        return pin;
    }

    public void setPin(PinBean pin) {
        this.pin = pin;
    }
}
