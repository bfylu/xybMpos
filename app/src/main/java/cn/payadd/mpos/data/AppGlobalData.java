package cn.payadd.mpos.data;

import android.content.Context;
import android.content.SharedPreferences;

import cn.payadd.mpos.AppContext;
import cn.payadd.mpos.constant.PreferencesKey;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public class AppGlobalData {

    /**
     * 全局配置
     */
    public static final String PREFERENCES_NAME = "mpos";

    public static void saveMPosKey(String sessionToken, String mposKey) {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PreferencesKey.MPOS_KEY, mposKey);
        editor.putString(PreferencesKey.SESSION_TOKEN, sessionToken);
        editor.commit();
    }

    public static String getSessionToken() {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String sessionToken = sp.getString(PreferencesKey.SESSION_TOKEN, null);
        return sessionToken;
    }

    public static String getMPosKey() {

        SharedPreferences sp = AppContext.instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String mposKey = sp.getString(PreferencesKey.MPOS_KEY, null);
        return mposKey;
    }

}
