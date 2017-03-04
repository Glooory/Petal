package com.glooory.petal.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.glooory.petal.app.Constants;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import common.PEApplication;

/**
 * Created by Glooory on 17/2/18.
 * SharedPreferences Utils.
 */

public class SPUtils {

    private static final String SP_COMMOM_FILE_NAME = "petal_shared_data";
    private static final String SP_HISTORY_ACCOUNTS_FILE_NAME = "petal_history_accounts";
    private static final String PREF_HISTORY_ACCOUNTS = "pref_history_accounts";
    private static final int SP_READ_WRITE_MODE = Context.MODE_PRIVATE;

    private SPUtils() {
    }

    /**
     * 异步提交的方法
     * @param key
     * @param object
     */
    public static void putByApply(String key, Object object) {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        judgeDataTypeToPut(editor, key, object);
        editor.apply();
    }

    /**
     * 同步提交的方法
     * @param key
     * @param object
     * @return
     */
    public static boolean putByCommit(String key, Object object) {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        judgeDataTypeToPut(editor, key, object);
        return editor.commit();
    }

    /**
     * 根据不同的数据类型，使用不同的写入方法
     * @param editor
     * @param key
     * @param object
     */
    private static void judgeDataTypeToPut(SharedPreferences.Editor editor,
            String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Set) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            editor.putString(key, object.toString());
        }
    }

    public static Object get(String key, Object defaultValue) {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE);

        if (defaultValue instanceof String) {
            return sharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        }else if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Set) {
            return sharedPreferences.getStringSet(key, (Set<String>) defaultValue);
        }

        return null;
    }

    /**
     * 移除 key 对应的值
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 判断某个 key 是否已经存在
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE);
        return sharedPreferences.contains(key);
    }

    /**
     * 获取所有的键值对
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE);
        return sharedPreferences.getAll();
    }

    /**
     * 获取所有的历史登录过的用户账号
     * @return
     */
    public static Set<String> getHistoryAccounts() {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_HISTORY_ACCOUNTS_FILE_NAME, SP_READ_WRITE_MODE);
        return sharedPreferences.getStringSet(PREF_HISTORY_ACCOUNTS, new HashSet<String>());
    }

    /**
     * 保存历史登录过的账号
     * @param accouts
     */
    public static void putHistoryAccounts(Set<String> accouts) {
        SharedPreferences sharedPreferences = PEApplication.getContext()
                .getSharedPreferences(SP_HISTORY_ACCOUNTS_FILE_NAME, SP_READ_WRITE_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(PREF_HISTORY_ACCOUNTS, accouts);
        editor.apply();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private final SharedPreferences.Editor mEditor = PEApplication.getContext()
                .getSharedPreferences(SP_COMMOM_FILE_NAME, SP_READ_WRITE_MODE).edit();

        public Builder addData(String key, Object object) {
            judgeDataTypeToPut(mEditor, key, object);
            return this;
        }

        public void build() {
            mEditor.apply();
        }
    }

    public static String getAuthorization() {
        boolean isLogin = (boolean) get(Constants.PREF_IS_LOGIN, false);
        if (isLogin) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(get(Constants.PREF_TOKEN_TYPE, " "))
                    .append(" ")
                    .append(get(Constants.PREF_TOKEN_ACCESS, " "));
            return stringBuilder.toString();
        }
        return BaseClientInfo.CLIENT_INFO_DEFAULT;
    }
}
