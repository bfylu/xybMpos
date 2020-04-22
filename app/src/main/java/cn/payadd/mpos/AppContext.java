package cn.payadd.mpos;

import android.app.Application;

/**
 * Created by zhengzhen.wang on 2016/12/2.
 */

public class AppContext extends Application {

    public static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        init();
    }

    private void init() {

    }

}
