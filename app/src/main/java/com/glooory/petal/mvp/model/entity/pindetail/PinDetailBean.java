package com.glooory.petal.mvp.model.entity.pindetail;

import com.glooory.petal.mvp.model.entity.BoardBean;
import com.glooory.petal.mvp.model.entity.FileBean;
import com.glooory.petal.mvp.model.entity.PinBean;
import com.glooory.petal.mvp.model.entity.UserBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/11 0011 14:58.
 */
public class PinDetailBean {

    private AdvancedPinBean pin;
    private boolean promotions;

    public static PinDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, PinDetailBean.class);
    }

    public AdvancedPinBean getPin() {
        return pin;
    }

    public void setPin(AdvancedPinBean pin) {
        this.pin = pin;
    }

    public boolean isPromotions() {
        return promotions;
    }

    public void setPromotions(boolean promotions) {
        this.promotions = promotions;
    }

    public static class AdvancedPinBean {
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
        private UserBean user;
        private BoardBean board;
        private UserBean via_user;
        private PinBean via_pin;
        private PinBean original_pin;
        private boolean hide_origin;
        private PinBean prev;
        private PinBean next;
        private boolean liked;
        private Object breadcrumb;
        private String category;
        private List<PinBean> repins;
        private List<CommentsBean> comments;
        private List<LikesBean> likes;
        private List<PinBean> siblings;
        private List<List<PinBean>> recommend;

        public static PinBean objectFromData(String str) {

            return new Gson().fromJson(str, PinBean.class);
        }

        public int getPinId() {
            return pin_id;
        }

        public void setPinId(int pinId) {
            this.pin_id = pinId;
        }

        public int getUserId() {
            return user_id;
        }

        public void setUserId(int userId) {
            this.user_id = userId;
        }

        public int getBoardId() {
            return board_id;
        }

        public void setBoardId(int boardId) {
            this.board_id = boardId;
        }

        public int getFileId() {
            return file_id;
        }

        public void setFileId(int fileId) {
            this.file_id = fileId;
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
            this.media_type = mediaType;
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
            this.raw_text = rawText;
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
            this.via_user_id = viaUserId;
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
            this.created_at = createdAt;
        }

        public int getLikeCount() {
            return like_count;
        }

        public void setLikeCount(int likeCount) {
            this.like_count = likeCount;
        }

        public int getCommentCount() {
            return comment_count;
        }

        public void setCommentCount(int commentCount) {
            this.comment_count = commentCount;
        }

        public int getRepinCount() {
            return repin_count;
        }

        public void setRepinCount(int repinCount) {
            this.repin_count = repinCount;
        }

        public int getIsPrivate() {
            return is_private;
        }

        public void setIsPrivate(int isPrivate) {
            this.is_private = isPrivate;
        }

        public String getOrigSource() {
            return orig_source;
        }

        public void setOrigSource(String origSource) {
            this.orig_source = origSource;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public BoardBean getBoard() {
            return board;
        }

        public void setBoard(BoardBean board) {
            this.board = board;
        }

        public UserBean getViaUser() {
            return via_user;
        }

        public void setViaUser(UserBean viaUser) {
            this.via_user = viaUser;
        }

        public PinBean getViaPin() {
            return via_pin;
        }

        public void setViaPin(PinBean viaPin) {
            this.via_pin = viaPin;
        }

        public PinBean getOriginalPin() {
            return original_pin;
        }

        public void setOriginalPin(PinBean originalPin) {
            this.original_pin = originalPin;
        }

        public boolean isHideOrigin() {
            return hide_origin;
        }

        public void setHideOrigin(boolean hideOrigin) {
            this.hide_origin = hideOrigin;
        }

        public PinBean getPrev() {
            return prev;
        }

        public void setPrev(PinBean prev) {
            this.prev = prev;
        }

        public PinBean getNext() {
            return next;
        }

        public void setNext(PinBean next) {
            this.next = next;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public Object getBreadcrumb() {
            return breadcrumb;
        }

        public void setBreadcrumb(Object breadcrumb) {
            this.breadcrumb = breadcrumb;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<PinBean> getRepins() {
            return repins;
        }

        public void setRepins(List<PinBean> repins) {
            this.repins = repins;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public List<LikesBean> getLikes() {
            return likes;
        }

        public void setLikes(List<LikesBean> likes) {
            this.likes = likes;
        }

        public List<PinBean> getSiblings() {
            return siblings;
        }

        public void setSiblings(List<PinBean> siblings) {
            this.siblings = siblings;
        }

        public List<List<PinBean>> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<List<PinBean>> recommend) {
            this.recommend = recommend;
        }

        public static class CommentsBean {
            private int comment_id;
            private int pin_id;
            private int user_id;
            private String raw_text;
            private int status;
            private int created_at;
            private UserBean user;

            public static CommentsBean objectFromData(String str) {

                return new Gson().fromJson(str, CommentsBean.class);
            }

            public int getCommentId() {
                return comment_id;
            }

            public void setCommentId(int commentId) {
                this.comment_id = commentId;
            }

            public int getPinId() {
                return pin_id;
            }

            public void setPinId(int pinId) {
                this.pin_id = pinId;
            }

            public int getUserId() {
                return user_id;
            }

            public void setUserId(int userId) {
                this.user_id = userId;
            }

            public String getRawText() {
                return raw_text;
            }

            public void setRawText(String rawText) {
                this.raw_text = rawText;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCreatedAt() {
                return created_at;
            }

            public void setCreatedAt(int createdAt) {
                this.created_at = createdAt;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }
        }

        public static class LikesBean {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            private FileBean avatar;
            private Object extra;
            private int liked_at;

            public static LikesBean objectFromData(String str) {

                return new Gson().fromJson(str, LikesBean.class);
            }

            public int getUserId() {
                return user_id;
            }

            public void setUserId(int userId) {
                this.user_id = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUrlname() {
                return urlname;
            }

            public void setUrlname(String urlname) {
                this.urlname = urlname;
            }

            public int getCreatedAt() {
                return created_at;
            }

            public void setCreatedAt(int createdAt) {
                this.created_at = createdAt;
            }

            public FileBean getAvatar() {
                return avatar;
            }

            public void setAvatar(FileBean avatar) {
                this.avatar = avatar;
            }

            public Object getExtra() {
                return extra;
            }

            public void setExtra(Object extra) {
                this.extra = extra;
            }

            public int getLikedAt() {
                return liked_at;
            }

            public void setLikedAt(int likedAt) {
                this.liked_at = likedAt;
            }
        }
    }
}
