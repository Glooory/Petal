package com.glooory.petal.mvp.model.entity.board;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/9/7 0007 16:50.
 */
public class BoardSingleBean {

    /**
     * board_id : 25394109
     * user_id : 10718490
     * title : 设计素材-动物
     * description :
     * category_id : photography
     * seq : 30
     * pin_count : 131
     * follow_count : 31
     * like_count : 1
     * created_at : 1442816593
     * updated_at : 1473236346
     * deleting : 0
     * is_private : 0
     * extra : null
     * user : {"user_id":10718490,"username":"小院青城","urlname":"l403315127","created_at":1384825969,
     * "avatar":{"id":112197876,"farm":"farm1","bucket":"hbimg",
     * "key":"a8954d7a5ee26c34c5313caa542aa361e60bd50530407-9Lnxz5","type":"image/jpeg",
     * "width":"862","height":"930","frames":"1"},"extra":null,"pin_count":14857,
     * "follower_count":457,"boards_like_count":0,"board_count":67,"commodity_count":24,
     * "like_count":10,"creations_count":0,"following_count":65,"profile":{},
     * "status":{"newbietask":0,"lr":1462270759,"invites":0,"share":"0"}}
     */

    private BoardBean board;

    public static BoardSingleBean objectFromData(String str) {

        return new Gson().fromJson(str, BoardSingleBean.class);
    }

    public BoardBean getBoard() {
        return board;
    }

    public void setBoard(BoardBean board) {
        this.board = board;
    }
}
