package com.glooory.petal.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class PinsBean implements Parcelable {

    /*
    PinBean
    "pin":{
        "pin_id":834408847,
        "user_id":17368129,
        "board_id":31489038,
        "file_id":112862047,
        "file":{                PinsFileBean
            "id":112862047,
            "farm":"farm1",
            "bucket":"hbimg",
            "key":"fda7fcbb8efbc9177556f2f2989b0a2f5d6008f414951e-UT8MUO",
            "type":"image/gif",
            "height":"275",
            "width":"274",
            "frames":"52"
            },
        "media_type":0,
        "source":"gifjia.com",
        "link":"http://www.gifjia.com/23246/2/",
        "raw_text":"【Mandy Kay】美女GIF 双马尾的性感黑丝辣妹",
        "text_meta":{},
        "via":2,
        "via_user_id":0,
        "original":null,
        "created_at":1472264524,
        "like_count":0,
        "comment_count":0,
        "repin_count":0,
        "is_private":0,
        "orig_source":"http://ww2.sinaimg.cn/mw690/e75a115bgw1f6b56oisbdg207m07nb29.gif",
        "user":{            PinsUserBean
            "user_id":17368129,
            "username":"hbk112",
            "urlname":"gain1123",
            "created_at":1432860604,
            "avatar":{
                "id":92107218,
                "farm":"farm1",
                "bucket":"hbimg",
                "key":"4591dc495d509736f69517c9480af27b7f7103072d931-QJWISP",
                "type":"image/jpeg",
                "height":"1500",
                "frames":"1",
                "width":"1000"
                },
            "extra":null
            },
        "board":{           PinsBoardBean
            "board_id":31489038,
            "user_id":17368129,
            "title":"美女gif",
            "description":"",
            "category_id":"apparel",
            "seq":9,
            "pin_count":9,
            "follow_count":23,
            "like_count":0,
            "created_at":1472264261,
            "updated_at":1472264524,
            "deleting":0,
            "is_private":0,
            "extra":
            "pins":[]
            }
    }
    */

    private int pin_id;
    private int user_id;
    private int board_id;
    private int file_id;

    private PinsFileBean file;
    private int media_type;
    private String source;
    private String link;
    private String raw_text;
    private int via;
    private int via_user_id;
    private int original;
    private int created_at;
    private int like_count;
    private int seq;
    private int comment_count;
    private int repin_count;
    private int is_private;
    private String orig_source;
    private boolean liked;

    private PinsUserBean user;

    private PinsBoardBean board;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getPin_id() {
        return pin_id;
    }

    public void setPin_id(int pin_id) {
        this.pin_id = pin_id;
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

    public PinsFileBean getFile() {
        return file;
    }

    public void setFile(PinsFileBean file) {
        this.file = file;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
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

    public String getRaw_text() {
        return raw_text;
    }

    public void setRaw_text(String raw_text) {
        this.raw_text = raw_text;
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

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
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

    public String getOrig_source() {
        return orig_source;
    }

    public void setOrig_source(String orig_source) {
        this.orig_source = orig_source;
    }

    public PinsUserBean getUser() {
        return user;
    }

    public void setUser(PinsUserBean user) {
        this.user = user;
    }

    public PinsBoardBean getBoard() {
        return board;
    }

    public void setBoard(PinsBoardBean board) {
        this.board = board;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pin_id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.board_id);
        dest.writeInt(this.file_id);
        dest.writeParcelable(this.file, flags);
        dest.writeInt(this.media_type);
        dest.writeString(this.source);
        dest.writeString(this.link);
        dest.writeString(this.raw_text);
        dest.writeInt(this.via);
        dest.writeInt(this.via_user_id);
        dest.writeInt(this.original);
        dest.writeInt(this.created_at);
        dest.writeInt(this.like_count);
        dest.writeInt(this.seq);
        dest.writeInt(this.comment_count);
        dest.writeInt(this.repin_count);
        dest.writeInt(this.is_private);
        dest.writeString(this.orig_source);
        dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.board, flags);
    }

    public PinsBean() {
    }

    protected PinsBean(Parcel in) {
        this.pin_id = in.readInt();
        this.user_id = in.readInt();
        this.board_id = in.readInt();
        this.file_id = in.readInt();
        this.file = in.readParcelable(PinsFileBean.class.getClassLoader());
        this.media_type = in.readInt();
        this.source = in.readString();
        this.link = in.readString();
        this.raw_text = in.readString();
        this.via = in.readInt();
        this.via_user_id = in.readInt();
        this.original = in.readInt();
        this.created_at = in.readInt();
        this.like_count = in.readInt();
        this.seq = in.readInt();
        this.comment_count = in.readInt();
        this.repin_count = in.readInt();
        this.is_private = in.readInt();
        this.orig_source = in.readString();
        this.liked = in.readByte() != 0;
        this.user = in.readParcelable(PinsUserBean.class.getClassLoader());
        this.board = in.readParcelable(PinsBoardBean.class.getClassLoader());
    }

    public static final Creator<PinsBean> CREATOR = new Creator<PinsBean>() {
        @Override
        public PinsBean createFromParcel(Parcel source) {
            return new PinsBean(source);
        }

        @Override
        public PinsBean[] newArray(int size) {
            return new PinsBean[size];
        }
    };

}
