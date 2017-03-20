package com.glooory.petal.app;

/**
 * Created by Glooory on 17/2/18.
 */

public class Constants {

    public static final String EMPTY_STRING = "";
    public static final String COMMA = ",";

    public static final String PREF_AUTHORIZATION = "authorization";

    public static final String PREF_TOKEN_ACCESS = "token_access";
    public static final String PREF_TOKEN_REFRESH = "token_refresh";
    public static final String PREF_TOKEN_TYPE = "token_type";
    public static final String PREF_TOKEN_EXPIRES_IN = "token_expires_in";

    public static final String PREF_IS_LOGIN = "is_login";
    public static final String PREF_LOGIN_TIME = "login_time";
    public static final String PREF_IS_SKIP_LOGIN = "is_skip_login";

    public static final String PREF_USER_ACCOUNT = "user_account";
    public static final String PREF_USER_PASSWORD = "user_password";
    public static final String PREF_USER_NAME = "user_name";
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USER_EMAIL = "user_email";
    public static final String PREF_USER_AVATAR_KEY = "user_avatar_key";
    public static final String PREF_BOARD_TITLES = "board_titles";
    public static final String PREF_BOARD_IDS = "board_ids";
    public static final String PREF_LAST_SAVE_BOARD = "last_save_board";

    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
    public static final String HTTP_QUERY_LIMIT = "limit";
    public static final String HTTP_QUERY_MAX = "max";
    public static final int PER_PAGE_LIMIT = 20;
    public static final String HTTP_ARGS_VALUE_PASSWORD = "password";
    public static final String HTTP_RECOMMEND_TAGS = "recommend_tags";

    public static final String EXTRA_PIN_ID = "pin_id";
    public static final String EXTRA_COLLECT_DES = "collect_des";
    public static final String EXTRA_BOARD_ID = "board_id";
    public static final String EXTRA_IS_COLLECTED = "is_collected";
    public static final String EXTRA_EXIST_IN = "exist_in";
    public static final String EXTRA_ASPECT_RATIO = "aspect_ratio";
    public static final String EXTRA_BASIC_COLOR = "basic_color";

    //防抖动时间间隔
    public static final int THROTTLE_DURATION = 300;

    public static final String IMAGE_TRANSITION_NAME = "image_transition";
}
