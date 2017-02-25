package com.glooory.petal.mvp.model.entity;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/7 0007 21:56.
 */
public class FollowerListBean {


    /**
     * board : {"board_id":28107681,"user_id":17133213,"title":"❀ 极度湿润",
     * "description":"水下霓裳，窒息的美","category_id":"photography","seq":43,"pin_count":863,
     * "follow_count":1105,"like_count":22,"created_at":1455163371,"updated_at":1473234817,
     * "deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"794318906"}},
     * "user":{"user_id":17133213,"username":"枕边梦♧","urlname":"asyul061","created_at":1430221819,
     * "avatar":{"id":84574020,"farm":"farm1","bucket":"hbimg",
     * "key":"d3cdcc613b8c575da383e2407cc0a46692ce10d0485fa-5Evxvo","type":"image/jpeg","width":920,
     * "height":764,"frames":1},"extra":{"is_museuser":true},"pin_count":27960,"board_count":56,
     * "boards_like_count":0,"like_count":327,"commodity_count":0,"creations_count":0,
     * "follower_count":3880,"following_count":122,"muse_board_count":1,
     * "profile":{"location":"  浙江  杭州","sex":"2","birthday":"1999-7-3","job":"网页设计",
     * "url":"http://huaban.com/asyul061/","about":"设计是一场修行   需要付出爱和坚持\n",
     * "show_verification":"weibo"},"status":{"newbietask":0,"lr":1462275267,"invites":0,
     * "share":"0","default_board":31142992}},"category_name":"摄影","following":false}
     * followers : [{"user_id":19340535,"username":"xaqpocAJ","urlname":"brszxvc9hb",
     * "created_at":1473252278,"avatar":{"id":113993777,"farm":"farm1","bucket":"hbimg",
     * "key":"45b3f490488df4ff705249d2e02b16a1b584a2de951-yNaeIs","type":"image/jpeg",
     * "height":"100","width":"100","frames":"1"},"extra":null,"seq":1105342516,"pins":[],
     * "follower_count":0,"board_count":0,"pin_count":0},{"user_id":19323387,"username":"X1PWJWxt",
     * "urlname":"l8ift5mp2w7","created_at":1472998045,"avatar":{"id":113659967,"farm":"farm1",
     * "bucket":"hbimg","key":"d6784e2f78d14b07571bfdc3b21352b88cde5e906357-u3xmXj",
     * "type":"image/jpeg","height":"1239","width":"1239","frames":"1"},"extra":null,
     * "seq":1105337606,"pins":[{"pin_id":846951902,"user_id":19323387,"board_id":31675826,
     * "file_id":78779687,"file":{"bucket":"hbimg",
     * "key":"43ad558aad78899b40c14debdaf5d083389ad7fc18ef9-g8z7T0","type":"image/jpeg",
     * "height":785,"width":440,"frames":1},"media_type":0,"source":"weibo.com",
     * "link":"http://weibo.com/5167198527/BAXXF9gOM?from=page_1005055167198527_profile&wvr=6&mod=weibotime",
     * "raw_text":"一组记事手帐+倒数日app，文艺の也是让人离不开呢！！","text_meta":{},"via":442240477,
     * "via_user_id":17372833,"original":442240477,"created_at":1473253419,"like_count":0,
     * "comment_count":0,"repin_count":0,"is_private":0,"orig_source":null}],"follower_count":0,
     * "board_count":1,"pin_count":1},{"user_id":16349215,"username":"品清","urlname":"pznxpluinc",
     * "created_at":1415929685,"avatar":{"id":113046728,"farm":"farm1","bucket":"hbimg",
     * "key":"8a718daab9b9fe4b084889d18d957ba08d08e395b28d-e3Z2LY","type":"image/jpeg",
     * "width":"140","height":"140","frames":"1"},"extra":{"is_museuser":true},"seq":1105325732,
     * "pins":[{"pin_id":846938495,"user_id":16349215,"board_id":24814415,"file_id":113993808,
     * "file":{"bucket":"hbimg","key":"e8e4e3cb728549b836d67851125a82040a6161bb6029a-yPZabm",
     * "type":"image/jpeg","height":"658","width":"637","frames":"1"},"media_type":0,
     * "source":"belsveta.net","link":"http://belsveta.net/photos.html","raw_text":"",
     * "text_meta":{},"via":846938017,"via_user_id":13267973,"original":846938017,
     * "created_at":1473252320,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,
     * "orig_source":null},{"pin_id":846928500,"user_id":16349215,"board_id":24396085,
     * "file_id":54017491,"file":{"bucket":"hbimg",
     * "key":"ed0e9d0afda8c6eaf11e71a6401daae5f3716806ea55-bdAql0","type":"image/jpeg",
     * "height":426,"width":567,"frames":1},"media_type":0,"source":"zcool.com.cn",
     * "link":"http://www.zcool.com.cn/work/ZNDkxNDU5Mg==.html","raw_text":"","text_meta":{},
     * "via":807305071,"via_user_id":17133213,"original":222856829,"created_at":1473251534,
     * "like_count":0,"comment_count":0,"repin_count":0,"is_private":0,
     * "orig_source":"http://zcimg.zcool.com.cn/zcimg/m_877b53e1b4a4000001673def50d6.jpg"},
     * {"pin_id":846928025,"user_id":16349215,"board_id":26402949,"file_id":105754880,
     * "file":{"bucket":"hbimg","key":"b4610aee36a169701b4df37957c2aaa4e1b4506ab08a-JGhcp8",
     * "type":"image/jpeg","height":"340","width":"505","frames":"1"},"media_type":0,
     * "source":null,"link":null,"raw_text":"","text_meta":{},"via":781005255,"via_user_id":17133213,
     * "original":751272717,"created_at":1473251492,"like_count":0,"comment_count":0,
     * "repin_count":0,"is_private":0,"orig_source":null}],"follower_count":573,"board_count":53,
     * "pin_count":16368},{"user_id":17929732,"username":"﹎囘メ菋","urlname":"y6hpw3oz4kp",
     * "created_at":1443493057,"avatar":{"id":82639513,"farm":"farm1","bucket":"hbimg",
     * "key":"b477d9e41c8ba1292b56c016ebe0aa39ee778c198d8-6b7Yfz","type":"image/jpeg","width":100,
     * "height":100,"frames":1},"extra":null,"seq":1105317356,"pins":[{"pin_id":846930441,
     * "user_id":17929732,"board_id":30333570,"file_id":89590773,"file":{"bucket":"hbimg",
     * "key":"7361caf5685cc12585e3a3182318982fcc9147777aa31-7fFcC6","type":"image/png",
     * "height":1482,"width":1006,"frames":1},"media_type":0,"source":null,"link":null,
     * "raw_text":"自己做了个祥云的立体","text_meta":{},"via":611596990,"via_user_id":17133213,
     * "original":567057604,"created_at":1473251703,"like_count":0,"comment_count":0,
     * "repin_count":0,"is_private":0,"orig_source":null},{"pin_id":846929990,"user_id":17929732,
     * "board_id":30329106,"file_id":68538685,"file":{"bucket":"hbimg",
     * "key":"eae7b161633bf04d43d6cab9815e73c6526e39bb5280d-hV0KbH","type":"image/jpeg",
     * "height":960,"width":640,"frames":1},"media_type":0,"source":null,"link":null,
     * "raw_text":"古风花纹","text_meta":{},"via":650470966,"via_user_id":17133213,"original":328822106,
     * "created_at":1473251668,"like_count":0,"comment_count":0,"repin_count":0,"is_private":0,
     * "orig_source":null},{"pin_id":846912116,"user_id":17929732,"board_id":30504940,
     * "file_id":111131136,"file":{"bucket":"hbimg",
     * "key":"8db155999d4fb8566128163459fbac2615e407fdafdc-5p6q55","type":"image/jpeg",
     * "height":"612","width":"612","frames":"1"},"media_type":0,"source":null,"link":null,
     * "raw_text":"","text_meta":{},"via":815262216,"via_user_id":17133213,
     * "original":814665288,"created_at":1473249977,"like_count":0,"comment_count":0,"repin_count":0,
     * "is_private":0,"orig_source":null}],"follower_count":18,"board_count":27,"pin_count":677},
     * {"user_id":19340153,"username":"爱生气的阿尔玛","urlname":"s3sowct0avq","created_at":1473247244,
     * "avatar":{"id":113989651,"farm":"farm1","bucket":"hbimg",
     * "key":"8a18df0964de4ec868496a7ba55b7654f46b46c2fb92-Poi4sl","type":"image/jpeg","width":"748",
     * "height":"750","frames":"1"},"extra":null,"seq":1105299768,"pins":[],"follower_count":0,
     * "board_count":0,"pin_count":0}]
     * board_title : ❀ 极度湿润
     */

    private BoardBean board;
    private String board_title;
    /**
     * user_id : 19340535
     * username : xaqpocAJ
     * urlname : brszxvc9hb
     * created_at : 1473252278
     * avatar : {"id":113993777,"farm":"farm1","bucket":"hbimg",
     * "key":"45b3f490488df4ff705249d2e02b16a1b584a2de951-yNaeIs","type":"image/jpeg",
     * "height":"100","width":"100","frames":"1"}
     * extra : null
     * seq : 1105342516
     * pins : []
     * follower_count : 0
     * board_count : 0
     * pin_count : 0
     */

    private List<FollowerBean> followers;

    public static FollowerListBean objectFromData(String str) {

        return new Gson().fromJson(str, FollowerListBean.class);
    }

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public List<FollowerBean> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowerBean> followers) {
        this.followers = followers;
    }
}
