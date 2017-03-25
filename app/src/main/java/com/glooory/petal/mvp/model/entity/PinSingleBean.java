package com.glooory.petal.mvp.model.entity;

/**
 * Created by Glooory on 17/3/25.
 */

/**
 * 修改采集后的返回结果
 */
public class PinSingleBean {


    /**
     * pin : {"pin_id":106608947,"user_id":1907038,"board_id":3210750,"file_id":2744509,
     * "file":{"bucket":"hbimg","key":"fc17d39ded26e6db23eacb5238c6305185ee8c60266cb-wEMBLJ",
     * "type":"image/jpeg","height":640,"width":427,"frames":1},
     * "media_type":0,"source":"68.media.tumblr.com",
     * "link":"http://68.media.tumblr.com/89a13521865c6452d1a1ab4f7f6b6484/tumblr_msmiteFEXm1rzxgwuo1_500.jpg",
     * "raw_text":"wusg","text_meta":{},"via":106801720,"via_user_id":1674047,
     * "original":104222345,"created_at":149076932,"like_count":0,"comment_count":0,
     * "repin_count":0,"is_private":0,"orig_source":null}
     */

    private PinBean pin;

    public PinBean getPin() {
        return pin;
    }

    public void setPin(PinBean pin) {
        this.pin = pin;
    }
}
