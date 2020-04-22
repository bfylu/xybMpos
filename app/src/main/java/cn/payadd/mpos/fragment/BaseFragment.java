package cn.payadd.mpos.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 是否执行onCreateView
     */
    private boolean isCreateView;

    protected Context mContext;

    private Toast shortToast, longToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(getLayoutId(), null);
        initView(v);
        initData();

        mContext = getActivity();
        isCreateView = true;

        return v;
    }

    public void showShortToast(String msg) {
        if (null == shortToast) {
            shortToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            shortToast.cancel();
            shortToast.setText(msg);
        }
        shortToast.show();
    }

    public void showLongToast(String msg) {
        if (null == longToast) {
            longToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        } else {
            longToast.cancel();
            longToast.setText(msg);
        }
        longToast.show();
    }

    public void showToast(String msg) {
        showShortToast(msg);
    }

    public abstract int getLayoutId();

    public abstract void initView(View v);

    public abstract void initData();

    public void setCreateView(boolean createView) {
        isCreateView = createView;
    }

    public boolean isCreateView() {
        return isCreateView;
    }

}
