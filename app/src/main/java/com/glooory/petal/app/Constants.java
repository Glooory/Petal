package com.glooory.petal.app;

import com.glooory.petal.R;

/**
 * Created by Glooory on 17/2/18.
 */

public class Constants {

    public static final String EMPTY_STRING = "";
    public static final String COMMA = ",";
    public static final String SPACE = " ";

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
    public static final String PREF_SEARCH_HISTORY = "search_history";

    public static final int PER_PAGE_LIMIT = 20;
    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
    public static final String HTTP_QUERY_LIMIT = "limit";
    public static final String HTTP_QUERY_MAX = "max";
    public static final String HTTP_ARGS_VALUE_PASSWORD = "password";
    public static final String HTTP_RECOMMEND_TAGS = "recommend_tags";
    public static final String HTTP_ARGS_LIKE = "like";
    public static final String HTTP_ARGS_UNLIKE = "unlike";
    public static final String HTTP_ARGS_FOLLOW = "follow";
    public static final String HTTP_ARGS_UNFOLLOW = "unfollow";
    public static final String HTTP_ARGS_DELETE = "DELETE";

    public static final String EXTRA_PIN_ID = "pin_id";
    public static final String EXTRA_COLLECT_DES = "collect_des";
    public static final String EXTRA_BOARD_ID = "board_id";
    public static final String EXTRA_IS_COLLECTED = "is_collected";
    public static final String EXTRA_EXIST_IN = "exist_in";
    public static final String EXTRA_ASPECT_RATIO = "aspect_ratio";
    public static final String EXTRA_BASIC_COLOR = "basic_color";
    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_USER_NAME = "user_name";
    public static final String EXTRA_SEARCH_KEYWORD = "search_keyword";
    public static final String BUNDLE_CATEGORY_VALUE = "category_value";

    //防抖动时间间隔
    public static final int THROTTLE_DURATION = 300;

    public static final String PIN_TRANSITION_NAME = "pin_transition";
    public static final String AVATAR_TRANSITION_NAME = "avatar_transition";

    public static int[] CATEGORY_ICON_RES_IDS = new int[]{
            R.drawable.ic_ui_ux_24dp, R.drawable.ic_design_24dp, R.drawable.ic_illustration_24dp,
            R.drawable.ic_home_faliture_24dp, R.drawable.ic_apparel_24dp, R.drawable.ic_men_24dp,
            R.drawable.ic_marriage_24dp, R.drawable.ic_industry_design_24dp, R.drawable.ic_photography_24dp,
            R.drawable.ic_hair_model_24dp, R.drawable.ic_food_drinks_24dp, R.drawable.ic_travling_24dp,
            R.drawable.ic_diy_crafts_24dp, R.drawable.ic_fitness_24dp, R.drawable.ic_kids_24dp,
            R.drawable.ic_pets_24dp, R.drawable.ic_quatos_24dp, R.drawable.ic_famouis_people_24dp,
            R.drawable.ic_beauty_24dp, R.drawable.ic_gifs_24dp, R.drawable.ic_geek_24dp,
            R.drawable.ic_anime_24dp, R.drawable.ic_archetature_24dp, R.drawable.ic_arts_24dp,
            R.drawable.ic_data_figure_24dp, R.drawable.ic_game_24dp, R.drawable.ic_cars_24dp,
            R.drawable.ic_film_24dp, R.drawable.ic_tips_24dp, R.drawable.ic_education_24dp,
            R.drawable.ic_sports_24dp, R.drawable.ic_funny_24dp
    };
}
