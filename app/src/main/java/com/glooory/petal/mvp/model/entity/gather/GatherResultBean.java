package com.glooory.petal.mvp.model.entity.gather;

import com.glooory.petal.mvp.model.entity.ErrorBaseBean;
import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/9/23 0023 20:40.
 */

public class GatherResultBean extends ErrorBaseBean {


    /**
     * user_id : 19322933
     * board_id : 32082956
     * file_id : 115542614
     * file : {"bucket":"hbimg","key":"e3352fa778d5d36000dd56c08108d753ad87bfb13f29c-HhFozr","type":"image/jpeg","height":"1920","width":"1080","frames":"1"}
     * media_type : 0
     * source : null
     * link : null
     * raw_text :
     * text_meta : {}
     * via : 9
     * via_user_id : 0
     * original : null
     * created_at : 1474634321
     * like_count : 0
     * comment_count : 0
     * repin_count : 0
     * is_private : 0
     * pin_id : 864668974
     * orig_source : null
     * board : {"board_id":32082956,"user_id":19322933,"title":"bnx","description":"","category_id":null,"seq":3,"pin_count":4,"follow_count":0,"like_count":0,"created_at":1474598228,"updated_at":1474634321,"deleting":0,"is_private":0,"extra":null}
     * is_shiji : false
     * share_button : 0
     */

    private PinBean pin;

    public static GatherResultBean objectFromData(String str) {

        return new Gson().fromJson(str, GatherResultBean.class);
    }

    public PinBean getPin() {
        return pin;
    }

    public void setPin(PinBean pin) {
        this.pin = pin;
    }

    public static class PinBean {
        private int user_id;
        private int board_id;
        private int file_id;
        /**
         * bucket : hbimg
         * key : e3352fa778d5d36000dd56c08108d753ad87bfb13f29c-HhFozr
         * type : image/jpeg
         * height : 1920
         * width : 1080
         * frames : 1
         */

        private FileBean file;
        private int media_type;
        private Object source;
        private Object link;
        private String raw_text;
        private TextMetaBean text_meta;
        private int via;
        private int via_user_id;
        private Object original;
        private int created_at;
        private int like_count;
        private int comment_count;
        private int repin_count;
        private int is_private;
        private int pin_id;
        private Object orig_source;
        /**
         * board_id : 32082956
         * user_id : 19322933
         * title : bnx
         * description :
         * category_id : null
         * seq : 3
         * pin_count : 4
         * follow_count : 0
         * like_count : 0
         * created_at : 1474598228
         * updated_at : 1474634321
         * deleting : 0
         * is_private : 0
         * extra : null
         */

        private BoardBean board;
        private boolean is_shiji;
        private String share_button;

        public static PinBean objectFromData(String str) {

            return new Gson().fromJson(str, PinBean.class);
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getBoard_id() {
            return board_id;
        }

        public void setBoard_id(int board_id) {
            this.board_id = board_id;
        }

        public int getFile_id() {
            return file_id;
        }

        public void setFile_id(int file_id) {
            this.file_id = file_id;
        }

        public FileBean getFile() {
            return file;
        }

        public void setFile(FileBean file) {
            this.file = file;
        }

        public int getMedia_type() {
            return media_type;
        }

        public void setMedia_type(int media_type) {
            this.media_type = media_type;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(Object source) {
            this.source = source;
        }

        public Object getLink() {
            return link;
        }

        public void setLink(Object link) {
            this.link = link;
        }

        public String getRaw_text() {
            return raw_text;
        }

        public void setRaw_text(String raw_text) {
            this.raw_text = raw_text;
        }

        public TextMetaBean getText_meta() {
            return text_meta;
        }

        public void setText_meta(TextMetaBean text_meta) {
            this.text_meta = text_meta;
        }

        public int getVia() {
            return via;
        }

        public void setVia(int via) {
            this.via = via;
        }

        public int getVia_user_id() {
            return via_user_id;
        }

        public void setVia_user_id(int via_user_id) {
            this.via_user_id = via_user_id;
        }

        public Object getOriginal() {
            return original;
        }

        public void setOriginal(Object original) {
            this.original = original;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getLike_count() {
            return like_count;
        }

        public void setLike_count(int like_count) {
            this.like_count = like_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getRepin_count() {
            return repin_count;
        }

        public void setRepin_count(int repin_count) {
            this.repin_count = repin_count;
        }

        public int getIs_private() {
            return is_private;
        }

        public void setIs_private(int is_private) {
            this.is_private = is_private;
        }

        public int getPin_id() {
            return pin_id;
        }

        public void setPin_id(int pin_id) {
            this.pin_id = pin_id;
        }

        public Object getOrig_source() {
            return orig_source;
        }

        public void setOrig_source(Object orig_source) {
            this.orig_source = orig_source;
        }

        public BoardBean getBoard() {
            return board;
        }

        public void setBoard(BoardBean board) {
            this.board = board;
        }

        public boolean isIs_shiji() {
            return is_shiji;
        }

        public void setIs_shiji(boolean is_shiji) {
            this.is_shiji = is_shiji;
        }

        public String getShare_button() {
            return share_button;
        }

        public void setShare_button(String share_button) {
            this.share_button = share_button;
        }

        public static class FileBean {
            private String bucket;
            private String key;
            private String type;
            private int height;
            private int width;
            private String frames;

            public static FileBean objectFromData(String str) {

                return new Gson().fromJson(str, FileBean.class);
            }

            public String getBucket() {
                return bucket;
            }

            public void setBucket(String bucket) {
                this.bucket = bucket;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getFrames() {
                return frames;
            }

            public void setFrames(String frames) {
                this.frames = frames;
            }
        }

        public static class TextMetaBean {
            public static TextMetaBean objectFromData(String str) {

                return new Gson().fromJson(str, TextMetaBean.class);
            }
        }

        public static class BoardBean {
            private int board_id;
            private int user_id;
            private String title;
            private String description;
            private Object category_id;
            private int seq;
            private int pin_count;
            private int follow_count;
            private int like_count;
            private int created_at;
            private int updated_at;
            private int deleting;
            private int is_private;
            private Object extra;

            public static BoardBean objectFromData(String str) {

                return new Gson().fromJson(str, BoardBean.class);
            }

            public int getBoard_id() {
                return board_id;
            }

            public void setBoard_id(int board_id) {
                this.board_id = board_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getCategory_id() {
                return category_id;
            }

            public void setCategory_id(Object category_id) {
                this.category_id = category_id;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public int getPin_count() {
                return pin_count;
            }

            public void setPin_count(int pin_count) {
                this.pin_count = pin_count;
            }

            public int getFollow_count() {
                return follow_count;
            }

            public void setFollow_count(int follow_count) {
                this.follow_count = follow_count;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public int getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(int updated_at) {
                this.updated_at = updated_at;
            }

            public int getDeleting() {
                return deleting;
            }

            public void setDeleting(int deleting) {
                this.deleting = deleting;
            }

            public int getIs_private() {
                return is_private;
            }

            public void setIs_private(int is_private) {
                this.is_private = is_private;
            }

            public Object getExtra() {
                return extra;
            }

            public void setExtra(Object extra) {
                this.extra = extra;
            }
        }
    }
}
