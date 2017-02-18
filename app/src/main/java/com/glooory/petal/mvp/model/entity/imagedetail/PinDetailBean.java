package com.glooory.petal.mvp.model.entity.imagedetail;

import com.glooory.petal.mvp.model.entity.user.UserBoardItemBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/11 0011 14:58.
 */
public class PinDetailBean {


    /**
     * pin_id : 832252280
     * user_id : 17202953
     * board_id : 22236960
     * file_id : 109407389
     * file : {"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"}
     * media_type : 0
     * source : behance.net
     * link : https://www.behance.net/gallery/40772623/Illusionary-Prisms
     * raw_text :
     * text_meta : {}
     * via : 831961636
     * via_user_id : 16745470
     * original : 831961636
     * created_at : 1472106083
     * like_count : 24
     * comment_count : 1
     * repin_count : 126
     * is_private : 0
     * orig_source : https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg
     */

    private PinBean pin;
    /**
     * pin : {"pin_id":832252280,"user_id":17202953,"board_id":22236960,"file_id":109407389,"file":{"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"},"media_type":0,"source":"behance.net","link":"https://www.behance.net/gallery/40772623/Illusionary-Prisms","raw_text":"","text_meta":{},"via":831961636,"via_user_id":16745470,"original":831961636,"created_at":1472106083,"like_count":24,"comment_count":1,"repin_count":126,"is_private":0,"orig_source":"https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg","user":{"user_id":17202953,"username":"达令☞","urlname":"tdlpukwogz","created_at":1430961383,"avatar":{"id":111551732,"farm":"farm1","bucket":"hbimg","key":"ce9069ec38d0d5af73ee970ae388d9c30c3dc3f05d060-K1OfBk","type":"image/jpeg","width":"640","height":"640","frames":"1"},"extra":{"is_museuser":true}},"board":{"board_id":22236960,"user_id":17202953,"title":"model☞","description":"【绝代有佳人，幽居在空谷】","category_id":"apparel","seq":9,"pin_count":1628,"follow_count":1395,"like_count":47,"created_at":1431478225,"updated_at":1473572831,"deleting":0,"is_private":0,"extra":{"cover":{"pin_id":"702438389"}}}]}
     * stores : {}
     * promotions : false
     * ads : {"fixedAds":[],"normalAds":[]}
     */

    private boolean promotions;

    public static PinDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, PinDetailBean.class);
    }

    public PinBean getPin() {
        return pin;
    }

    public void setPin(PinBean pin) {
        this.pin = pin;
    }

    public boolean isPromotions() {
        return promotions;
    }

    public void setPromotions(boolean promotions) {
        this.promotions = promotions;
    }

    public static class PinBean {
        private int pin_id;
        private int user_id;
        private int board_id;
        private int file_id;
        /**
         * id : 109407389
         * farm : farm1
         * bucket : hbimg
         * key : 43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms
         * type : image/jpeg
         * height : 1000
         * frames : 1
         * width : 667
         * colors : [{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}]
         * theme : ff9999
         */

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
        /**
         * user_id : 17202953
         * username : 达令☞
         * urlname : tdlpukwogz
         * created_at : 1430961383
         * avatar : {"id":111551732,"farm":"farm1","bucket":"hbimg","key":"ce9069ec38d0d5af73ee970ae388d9c30c3dc3f05d060-K1OfBk","type":"image/jpeg","width":"640","height":"640","frames":"1"}
         * extra : {"is_museuser":true}
         */

        private UserBean user;
        /**
         * board_id : 22236960
         * user_id : 17202953
         * title : model☞
         * description : 【绝代有佳人，幽居在空谷】
         * category_id : apparel
         * seq : 9
         * pin_count : 1628
         * follow_count : 1395
         * like_count : 47
         * created_at : 1431478225
         * updated_at : 1473572831
         * deleting : 0
         * is_private : 0
         * extra : {"cover":{"pin_id":"702438389"}}
         * following : false
         */

        private UserBoardItemBean board;
        private UserBean via_user;
        /**
         * pin_id : 831961636
         * user_id : 16745470
         * board_id : 27352895
         * file_id : 109407389
         * file : {"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"}
         * media_type : 0
         * source : behance.net
         * link : https://www.behance.net/gallery/40772623/Illusionary-Prisms
         * raw_text : Illusionary Prisms : Photographers: Gabriel Isak, Roberto GaxiolaStylist: Sarahy CisnerosModel: Madison KannaMUAH: Danielle Fisk Assistant MUAH: Ashley Goodman
         * text_meta : {}
         * via : 2
         * via_user_id : 0
         * original : null
         * created_at : 1472094533
         * like_count : 1
         * comment_count : 0
         * repin_count : 154
         * is_private : 0
         * orig_source : https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg
         * board : {"board_id":27352895,"user_id":16745470,"title":"人像","description":"","category_id":"photography","seq":6,"pin_count":651,"follow_count":453,"like_count":0,"created_at":1449137300,"updated_at":1472782982,"deleting":0,"is_private":0,"extra":null}
         */

        private ViaPinBean via_pin;
        private ViaPinBean original_pin;
        private boolean hide_origin;
        private PinsBean prev;
        private PinsBean next;
        private boolean liked;
        private Object breadcrumb;
        private String category;
        /**
         * pin_id : 850420597
         * user_id : 18560749
         * board_id : 30931703
         * file_id : 109407389
         * file : {"id":109407389,"farm":"farm1","bucket":"hbimg","key":"43fe098c3f8da62bcf4b0bc7eb5cc9aa215e45a544d10-qjtIms","type":"image/jpeg","height":"1000","frames":"1","width":"667","colors":[{"color":16751001,"ratio":0.34},{"color":16743552,"ratio":0.13},{"color":13408614,"ratio":0.13}],"theme":"ff9999"}
         * media_type : 0
         * source : behance.net
         * link : https://www.behance.net/gallery/40772623/Illusionary-Prisms
         * raw_text :
         * text_meta : {}
         * via : 832252280
         * via_user_id : 17202953
         * original : 831961636
         * created_at : 1473551403
         * like_count : 0
         * comment_count : 0
         * repin_count : 0
         * is_private : 0
         * orig_source : https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/cda23c40772623.578e51c5ecc75.jpg
         */

        private List<RepinsBean> repins;
        /**
         * comment_id : 2032566
         * pin_id : 832252280
         * user_id : 17191514
         * raw_text : 好喜欢
         * text_meta : {}
         * status : 1
         * created_at : 1473044455
         * user : {"user_id":17191514,"username":"吉吉黄a","urlname":"d56rxp0zm7b","created_at":1430907509,"avatar":{"id":108633138,"farm":"farm1","bucket":"hbimg","key":"ca2819bb6b5bc042a035e5a2b71ca71f5c6981d111b2a-yaWO0l","type":"image/jpeg","height":"748","width":"748","frames":"1"},"extra":null}
         */

        private List<CommentsBean> comments;
        /**
         * user_id : 10676744
         * username : Arne
         * urlname : arne369963
         * created_at : 1384579559
         * avatar : {"id":32507132,"farm":"farm1","bucket":"hbimg","key":"e60471518be7614d3e79149ec54b16122e04d299a5a-rJ0G7D","type":"image/jpeg","width":100,"height":100,"frames":1}
         * extra : null
         * liked_at : 1472962442
         */

        private List<LikesBean> likes;
        /**
         * pin_id : 850629615
         * user_id : 8937961
         * board_id : 31734075
         * file_id : 59748860
         * file : {"id":59748860,"farm":"farm1","bucket":"hbimg","key":"9fa941210887c13416be03753de4ad28bb9477ed29c4c-S5dU0j","type":"image/jpeg","width":600,"height":921,"frames":1}
         * media_type : 0
         * source : behance.net
         * link : https://www.behance.net/gallery/19365883/Zombie-Island-game
         * raw_text : &quot;Zombie Island&quot; game : Concept, modeling, texturing, rendering and importing objects for &quot;Zombie Island&quot; game
         * text_meta : {}
         * via : 564044035
         * via_user_id : 16611417
         * original : 248544209
         * created_at : 1473576979
         * like_count : 0
         * comment_count : 0
         * repin_count : 0
         * is_private : 0
         * orig_source : null
         * user : {"user_id":8937961,"username":"HsuChihmo","urlname":"q949tly5rzq","created_at":1374756297,"avatar":{"id":25370401,"farm":"farm1","bucket":"hbimg","key":"e3cc79ba26a95094b152321029c7ff2e753a989f8e2-fcVwSL","type":"image/jpeg","width":50,"height":50,"frames":1},"extra":null}
         * board : {"board_id":31734075,"user_id":8937961,"title":"2","description":"","category_id":"illustration","seq":2,"pin_count":94,"follow_count":0,"like_count":0,"created_at":1473576309,"updated_at":1473576979,"deleting":0,"is_private":0,"extra":null}
         * via_user : {"user_id":16611417,"username":"古三儿","urlname":"kxrjjmukar","created_at":1417327141,"avatar":{"id":21122412,"farm":"farm1","bucket":"hbimg","key":"66b95a3bfbfede1894941942996a3c3075122c6519a-fJ3ci9","type":"image/jpeg","width":100,"height":100,"frames":1},"extra":null}
         */

        private List<SiblingsBean> siblings;
        private List<List<RepinsBean>> recommend;

        public static PinBean objectFromData(String str) {

            return new Gson().fromJson(str, PinBean.class);
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public UserBoardItemBean getBoard() {
            return board;
        }

        public void setBoard(UserBoardItemBean board) {
            this.board = board;
        }

        public UserBean getVia_user() {
            return via_user;
        }

        public void setVia_user(UserBean via_user) {
            this.via_user = via_user;
        }

        public ViaPinBean getVia_pin() {
            return via_pin;
        }

        public void setVia_pin(ViaPinBean via_pin) {
            this.via_pin = via_pin;
        }

        public ViaPinBean getOriginal_pin() {
            return original_pin;
        }

        public void setOriginal_pin(ViaPinBean original_pin) {
            this.original_pin = original_pin;
        }

        public boolean isHide_origin() {
            return hide_origin;
        }

        public void setHide_origin(boolean hide_origin) {
            this.hide_origin = hide_origin;
        }

        public PinsBean getPrev() {
            return prev;
        }

        public void setPrev(PinsBean prev) {
            this.prev = prev;
        }

        public PinsBean getNext() {
            return next;
        }

        public void setNext(PinsBean next) {
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

        public List<RepinsBean> getRepins() {
            return repins;
        }

        public void setRepins(List<RepinsBean> repins) {
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

        public List<SiblingsBean> getSiblings() {
            return siblings;
        }

        public void setSiblings(List<SiblingsBean> siblings) {
            this.siblings = siblings;
        }

        public List<List<RepinsBean>> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<List<RepinsBean>> recommend) {
            this.recommend = recommend;
        }

        public static class FileBean {
            private int id;
            private String farm;
            private String bucket;
            private String key;
            private String type;
            private int height;
            private String frames;
            private int width;
            private String theme;
            /**
             * color : 16751001
             * ratio : 0.34
             */

            private List<ColorsBean> colors;

            public static FileBean objectFromData(String str) {

                return new Gson().fromJson(str, FileBean.class);
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFarm() {
                return farm;
            }

            public void setFarm(String farm) {
                this.farm = farm;
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

            public String getFrames() {
                return frames;
            }

            public void setFrames(String frames) {
                this.frames = frames;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public String getTheme() {
                return theme;
            }

            public void setTheme(String theme) {
                this.theme = theme;
            }

            public List<ColorsBean> getColors() {
                return colors;
            }

            public void setColors(List<ColorsBean> colors) {
                this.colors = colors;
            }

            public static class ColorsBean {
                private int color;
                private double ratio;

                public static ColorsBean objectFromData(String str) {

                    return new Gson().fromJson(str, ColorsBean.class);
                }

                public int getColor() {
                    return color;
                }

                public void setColor(int color) {
                    this.color = color;
                }

                public double getRatio() {
                    return ratio;
                }

                public void setRatio(double ratio) {
                    this.ratio = ratio;
                }
            }
        }

        public static class UserBean {
            private int user_id;
            private String username;
            private String urlname;
            private int created_at;
            private FileBean avatar;
            /**
             * is_museuser : true
             */

            private ExtraBean extra;

            public static UserBean objectFromData(String str) {

                return new Gson().fromJson(str, UserBean.class);
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public FileBean getAvatar() {
                return avatar;
            }

            public void setAvatar(FileBean avatar) {
                this.avatar = avatar;
            }

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }

            public static class ExtraBean {
                private boolean is_museuser;

                public static ExtraBean objectFromData(String str) {

                    return new Gson().fromJson(str, ExtraBean.class);
                }

                public boolean isIs_museuser() {
                    return is_museuser;
                }

                public void setIs_museuser(boolean is_museuser) {
                    this.is_museuser = is_museuser;
                }
            }
        }

        public static class ViaPinBean {
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
            private Object original;
            private int created_at;
            private int like_count;
            private int comment_count;
            private int repin_count;
            private int is_private;
            private String orig_source;
            private UserBoardItemBean board;

            public static ViaPinBean objectFromData(String str) {

                return new Gson().fromJson(str, ViaPinBean.class);
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

            public String getOrig_source() {
                return orig_source;
            }

            public void setOrig_source(String orig_source) {
                this.orig_source = orig_source;
            }

            public UserBoardItemBean getBoard() {
                return board;
            }

            public void setBoard(UserBoardItemBean board) {
                this.board = board;
            }
        }

        public static class RepinsBean {
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
            private UserBoardItemBean board;

            public static RepinsBean objectFromData(String str) {

                return new Gson().fromJson(str, RepinsBean.class);
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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public UserBoardItemBean getBoard() {
                return board;
            }

            public void setBoard(UserBoardItemBean board) {
                this.board = board;
            }
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

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
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

            public String getRaw_text() {
                return raw_text;
            }

            public void setRaw_text(String raw_text) {
                this.raw_text = raw_text;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
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

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
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

            public int getLiked_at() {
                return liked_at;
            }

            public void setLiked_at(int liked_at) {
                this.liked_at = liked_at;
            }
        }

        public static class SiblingsBean {
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
            private Object orig_source;
            private UserBean user;
            private UserBoardItemBean board;
            private UserBean via_user;

            public static SiblingsBean objectFromData(String str) {

                return new Gson().fromJson(str, SiblingsBean.class);
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

            public Object getOrig_source() {
                return orig_source;
            }

            public void setOrig_source(Object orig_source) {
                this.orig_source = orig_source;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public UserBoardItemBean getBoard() {
                return board;
            }

            public void setBoard(UserBoardItemBean board) {
                this.board = board;
            }

            public UserBean getVia_user() {
                return via_user;
            }

            public void setVia_user(UserBean via_user) {
                this.via_user = via_user;
            }
        }
    }

    private static class PinsBean {
    }
}
