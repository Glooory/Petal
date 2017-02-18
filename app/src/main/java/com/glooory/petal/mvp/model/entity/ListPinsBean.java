package com.glooory.petal.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/15 0015 17:18.
 * 登录后首页请求数据的实体
 */
public class ListPinsBean {


    /**
     * followed_board : 67
     * suggestion_friends : []
     * ads : {"normalAds":[],"fixedAds":[]}
     */

    private int followed_board;
    /**
     * pin_id : 854798839
     * user_id : 106200
     * board_id : 326956
     * file_id : 113130338
     * file : {"id":113130338,"farm":"farm1","bucket":"hbimg","key":"571faa45fc36e1ac03eabc627c4d3b5ac52df06128ca0-QRsSeb","type":"image/jpeg","height":"1080","width":"1080","frames":"1","colors":[{"color":16250871,"ratio":0.18},{"color":16739921,"ratio":0.1},{"color":460551,"ratio":0.1}],"theme":"f7f7f7"}
     * media_type : 0
     * source : instagram.com
     * link : https://www.instagram.com/p/BJuALUHBC1Q/?taken-by=choco.linefriends
     * raw_text :
     * text_meta : {}
     * via : 844816456
     * via_user_id : 7366645
     * original : 837390580
     * created_at : 1473883573
     * like_count : 2
     * comment_count : 0
     * repin_count : 10
     * is_private : 0
     * orig_source : null
     * board : {"board_id":326956,"user_id":106200,"title":"Icon.Emoticon","description":"","category_id":"web_app_icon","seq":21,"pin_count":1600,"follow_count":35361,"like_count":138,"created_at":1330788182,"updated_at":1473883573,"deleting":0,"is_private":0,"extra":null}
     * user : {"user_id":106200,"username":"安の微笑","urlname":"kinsey","created_at":1330427554,"avatar":{"id":77649352,"farm":"farm1","bucket":"hbimg","key":"c2fa924dedb62f76898659cb7e239e12fb4a3612281ed-XEKces","type":"image/jpeg","width":1239,"height":1242,"frames":1},"extra":null}
     * via_user : {"user_id":7366645,"username":"阴天别闹了","urlname":"af4zkzjudd","created_at":1366678211,"avatar":{"id":32476273,"farm":"farm1","bucket":"hbimg","key":"0e13df4625153fd3065397672ca6018363ae1cd02518-vIXm1W","type":"image/jpeg","width":180,"height":180,"frames":1},"extra":{"is_museuser":true}}
     * liked : false
     */

    private List<PinsBean> pins;

    public static ListPinsBean objectFromData(String str) {

        return new Gson().fromJson(str, ListPinsBean.class);
    }

    public int getFollowed_board() {
        return followed_board;
    }

    public void setFollowed_board(int followed_board) {
        this.followed_board = followed_board;
    }

    public List<PinsBean> getPins() {
        return pins;
    }

    public void setPins(List<PinsBean> pins) {
        this.pins = pins;
    }

}
