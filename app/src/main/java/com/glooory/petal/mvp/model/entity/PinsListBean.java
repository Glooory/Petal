package com.glooory.petal.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class PinsListBean {
    //主页网络请求的Bean

    /**
     * pin_id : 834408847
     * user_id : 17368129
     * board_id : 31489038
     * file_id : 112862047
     * file : {"id":112862047,"farm":"farm1","bucket":"hbimg","key":"fda7fcbb8efbc9177556f2f2989b0a2f5d6008f414951e-UT8MUO","type":"image/gif","height":"275","width":"274","frames":"52"}
     * media_type : 0
     * source : gifjia.com
     * link : http://www.gifjia.com/23246/2/
     * raw_text : 【Mandy Kay】美女GIF 双马尾的辣妹
     * text_meta : {}
     * via : 2
     * via_user_id : 0
     * original : null
     * created_at : 1472264524
     * like_count : 0
     * comment_count : 0
     * repin_count : 0
     * is_private : 0
     * orig_source : http://ww2.sinaimg.cn/mw690/e75a115bgw1f6b56oisbdg207m07nb29.gif
     * user : {"user_id":17368129,"username":"hbk112","urlname":"gain1123","created_at":1432860604,"avatar":{"id":92107218,"farm":"farm1","bucket":"hbimg","key":"4591dc495d509736f69517c9480af27b7f7103072d931-QJWISP","type":"image/jpeg","height":"1500","frames":"1","width":"1000"},"extra":null}
     * board : {"board_id":31489038,"user_id":17368129,"title":"美女gif","description":"","category_id":"apparel","seq":9,"pin_count":9,"follow_count":23,"like_count":0,"created_at":1472264261,"updated_at":1472264524,"deleting":0,"is_private":0,"extra":null}
     */

    private List<com.glooory.petal.mvp.model.entity.PinsBean> pins;

    public static PinsListBean objectFromData(String str) {

        return new Gson().fromJson(str, PinsListBean.class);
    }

    public List<PinsBean> getPins() {
        return pins;
    }

    public void setPins(List<PinsBean> pins) {
        this.pins = pins;
    }

}
