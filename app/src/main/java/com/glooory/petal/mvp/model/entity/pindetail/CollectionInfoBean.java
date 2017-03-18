package com.glooory.petal.mvp.model.entity.pindetail;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.FileBean;

/**
 * Created by Glooory on 2016/9/12 0012 19:54.
 * 某个图片是否已经在自己的采集中的信息
 */
public class CollectionInfoBean {

    /**
     * warning : 100
     * exist_pin : {"pin_id":707960382,"user_id":15246080,"board_id":17891564,"file_id":76634908,
     * "file":{"farm":"farm1","bucket":"hbimg",
     * "key":"480ff4b6c1ee8f5a6facf64baf2138c6dcf3361c7cb2c-5cHQTC","type":"image/jpeg",
     * "width":638,"height":1136,"frames":1},"media_type":0,"source":null,"link":null,"raw_text":"",
     * "via":707907583,"via_user_id":12082335,"original":417098788,"created_at":1462517186,
     * "like_count":0,"comment_count":0,"repin_count":0,"is_private":0,"orig_source":null,
     * "board":{"board_id":17891564,"user_id":15246080,"title":"主画板修改","description":"",
     * "category_id":"beauty","seq":3,"pin_count":559,"follow_count":107,"like_count":0,
     * "created_at":1412310925,"updated_at":1462517187,"deleting":0,"is_private":0}}
     */

    private int warning;
    private ExistPinBean exist_pin;

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public ExistPinBean getExistPin() {
        return exist_pin;
    }

    public void setExistPin(ExistPinBean existPin) {
        this.exist_pin = exist_pin;
    }

    public static class ExistPinBean {

        /**
         * pin_id : 707960382
         * user_id : 15246080
         * board_id : 17891564
         * file_id : 76634908
         * file : {"farm":"farm1","bucket":"hbimg",
         * "key":"480ff4b6c1ee8f5a6facf64baf2138c6dcf3361c7cb2c-5cHQTC","type":"image/jpeg",
         * "width":638,"height":1136,"frames":1}
         * media_type : 0
         * source : null
         * link : null
         * raw_text :
         * via : 707907583
         * via_user_id : 12082335
         * original : 417098788
         * created_at : 1462517186
         * like_count : 0
         * comment_count : 0
         * repin_count : 0
         * is_private : 0
         * orig_source : null
         * board : {"board_id":17891564,"user_id":15246080,"title":"主画板修改","description":"",
         * "category_id":"beauty","seq":3,"pin_count":559,"follow_count":107,"like_count":0,
         * "created_at":1412310925,"updated_at":1462517187,"deleting":0,"is_private":0}
         */

        private int pin_id;
        private int user_id;
        private int board_id;
        private int file_id;
        private FileBean file;
        private int media_type;
        private String source;
        private String link;
        private String raw_text;
        private int via;
        private int via_user_id;
        private int original;
        private int created_at;
        private int like_count;
        private int comment_count;
        private int repin_count;
        private int is_private;
        private String orig_source;
        private BoardBean board;

        public int getPinId() {
            return pin_id;
        }

        public void setPinId(int pinId) {
            this.pin_id = pin_id;
        }

        public int getUserId() {
            return user_id;
        }

        public void setUserId(int userId) {
            this.user_id = user_id;
        }

        public int getBoardId() {
            return board_id;
        }

        public void setBoardId(int boardId) {
            this.board_id = board_id;
        }

        public int getFileId() {
            return file_id;
        }

        public void setFileId(int fileId) {
            this.file_id = file_id;
        }

        public FileBean getFile() {
            return file;
        }

        public void setFile(FileBean file) {
            this.file = file;
        }

        public int getMediaType() {
            return media_type;
        }

        public void setMediaType(int mediaType) {
            this.media_type = media_type;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getRawText() {
            return raw_text;
        }

        public void setRawText(String rawText) {
            this.raw_text = raw_text;
        }

        public int getVia() {
            return via;
        }

        public void setVia(int via) {
            this.via = via;
        }

        public int getViaUserId() {
            return via_user_id;
        }

        public void setViaUserId(int viaUserId) {
            this.via_user_id = via_user_id;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
        }

        public int getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(int createdAt) {
            this.created_at = created_at;
        }

        public int getLikeCount() {
            return like_count;
        }

        public void setLikeCount(int likeCount) {
            this.like_count = like_count;
        }

        public int getCommentCount() {
            return comment_count;
        }

        public void setCommentCount(int commentCount) {
            this.comment_count = comment_count;
        }

        public int getRepinCount() {
            return repin_count;
        }

        public void setRepinCount(int repinCount) {
            this.repin_count = repin_count;
        }

        public int getIsPrivate() {
            return is_private;
        }

        public void setIsPrivate(int isPrivate) {
            this.is_private = is_private;
        }

        public String getOrigSource() {
            return orig_source;
        }

        public void setOrigSource(String origSource) {
            this.orig_source = orig_source;
        }

        public BoardBean getBoard() {
            return board;
        }

        public void setBoard(BoardBean board) {
            this.board = board;
        }
    }
}
