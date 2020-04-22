package cn.payadd.mpos.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

import cn.payadd.mpos.R;
import cn.payadd.mpos.fragment.BaseFragment;
import cn.payadd.mpos.fragment.HomeFragment;
import cn.payadd.mpos.fragment.MineFragment;
import cn.payadd.mpos.fragment.NotifyFragment;
import cn.payadd.mpos.fragment.StatisticsFragment;
import cn.payadd.mpos.util.ThreadUtil;

/**
 * Created by zhengzhen.wang on 2016/12/2.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager fm;
    private BaseFragment curFragment, homeFragment, statisticsFragment, notifyFragment, mineFragment;

    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    void initView() {

        findViewById(R.id.btn_main_home).setOnClickListener(this);
        findViewById(R.id.btn_main_statistics).setOnClickListener(this);
        findViewById(R.id.btn_main_notify).setOnClickListener(this);
        findViewById(R.id.btn_main_mine).setOnClickListener(this);

        fm = getFragmentManager();

        switchMenu(R.id.btn_main_home);
    }

    @Override
    void initData() {

    }

    @Override
    void initPermission() {

    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        switch (viewId) {

            case R.id.btn_main_home: {

                switchMenu(viewId);
                break;
            }

            case R.id.btn_main_statistics: {

                switchMenu(viewId);
                break;
            }

            case R.id.btn_main_notify: {

                switchMenu(viewId);
                break;
            }

            case R.id.btn_main_mine: {

                switchMenu(viewId);
                break;
            }
        }
    }

    public void switchMenu(int id) {

        BaseFragment fragment = null;
        boolean isNew = false;
        switch (id) {
            case R.id.btn_main_home: {
                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                    isNew = true;
                }
                fragment = homeFragment;
                break;
            }
            case R.id.btn_main_statistics: {
                if (null == statisticsFragment) {
                    statisticsFragment = new StatisticsFragment();
                    isNew = true;
                }
                fragment = statisticsFragment;
                break;
            }
            case R.id.btn_main_notify: {
                if (null == notifyFragment) {
                    notifyFragment = new NotifyFragment();
                    isNew = true;
                }
                fragment = notifyFragment;
                break;
            }
            case R.id.btn_main_mine: {
                if (null == mineFragment) {
                    mineFragment = new MineFragment();
                    isNew = true;
                }
                fragment = mineFragment;
                break;
            }
        }

        if (curFragment == fragment) {
            return;
        }

        FragmentTransaction ft = fm.beginTransaction();
        if (null != curFragment) {
            ft.hide(curFragment);
        }
        if (isNew) {
            ft.add(R.id.fl_content, fragment);
        } else {
            ft.show(fragment);
        }
        ft.commit();
        curFragment = fragment;
    }

    @Override
    public void finish() {
        super.finish();
        ThreadUtil.close();
    }
}
