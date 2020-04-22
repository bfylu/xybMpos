package cn.payadd.mpos.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import cn.payadd.mpos.R;
import cn.payadd.mpos.fragment.AcquireAmountFragment;
import cn.payadd.mpos.fragment.AcquireScanFragment;
import cn.payadd.mpos.util.AmountUtil;
import cn.payadd.mpos.view.IAcquireView;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public class AcquireActivity extends BaseActivity implements IAcquireView {

    private FragmentManager fm;
    private AcquireAmountFragment acqAmtFragment;
    private AcquireScanFragment acqScanFragment;

    @Override
    int getLayoutId() {
        return R.layout.activity_acquire;
    }

    @Override
    void initView() {

        fm = getFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        acqAmtFragment = new AcquireAmountFragment();
        ft.add(R.id.fl_content, acqAmtFragment);
        ft.commit();
    }

    @Override
    void initData() {

    }

    @Override
    void initPermission() {

    }

    @Override
    public void toAcquireScan(BigDecimal amt) {

        FragmentTransaction ft = fm.beginTransaction();
        ft.hide(acqAmtFragment);
        if (null != acqScanFragment) {
            ft.remove(acqScanFragment);
        }
        AcquireScanFragment newFragment = new AcquireScanFragment();
        Bundle args = new Bundle();
        args.putString(AcquireScanFragment.KEY_PARAM_AMOUNT, AmountUtil.format(amt));
        newFragment.setArguments(args);
        ft.add(R.id.fl_content, newFragment);
        ft.addToBackStack(null);
        ft.commit();

        acqScanFragment = newFragment;
    }

}
