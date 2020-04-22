package cn.payadd.mpos.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toast shortToast, longToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();
        initData();
        initPermission();
    }

    abstract int getLayoutId();

    abstract void initView();

    abstract void initData();

    abstract void initPermission();

    public void showShortToast(String msg) {
        if (null == shortToast) {
            shortToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            shortToast.cancel();
            shortToast.setText(msg);
        }
        shortToast.show();
    }

    public void showLongToast(String msg) {
        if (null == longToast) {
            longToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        } else {
            longToast.cancel();
            longToast.setText(msg);
        }
        longToast.show();
    }

    public void showToast(String msg) {
        showShortToast(msg);
    }
}
