package cn.payadd.mpos.data;

import android.content.Context;
import android.content.SharedPreferences;

import cn.payadd.mpos.AppContext;
import cn.payadd.mpos.constant.PreferencesKey;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public class UserData {

    /**
     * 用户信息
     */
    public static final String PREFERENCES_NAME = "user";

    public static void saveUsername(String username) {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PreferencesKey.USERNAME, username);
        editor.commit();
    }

    public static void saveLoginToken(String loginToken) {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PreferencesKey.LOGIN_TOKEN, loginToken);
        editor.commit();
    }

    public static void removeLoginToken() {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(PreferencesKey.LOGIN_TOKEN);
        editor.commit();
    }

    public static String getUsername() {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String username = sp.getString(PreferencesKey.USERNAME, null);
        return username;
    }

    public static String getLoginToken() {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String loginToken = sp.getString(PreferencesKey.LOGIN_TOKEN, null);
        return loginToken;
    }
}
