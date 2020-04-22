package cn.payadd.mpos.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;

import cn.payadd.mpos.R;
import cn.payadd.mpos.view.IAcquireView;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public class AcquireAmountFragment extends BaseFragment implements View.OnClickListener {

    private IAcquireView mAcqView;
    private EditText etAcquireAmount;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_acquire_amount;
    }

    @Override
    public void initView(View v) {

        mAcqView = (IAcquireView) getActivity();

        etAcquireAmount = (EditText) v.findViewById(R.id.et_acquire_amount);
        v.findViewById(R.id.btn_confirm_acquire).setOnClickListener(this);
    }

    @Override
    public void initData() {


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_confirm_acquire: {
                String amtStr = etAcquireAmount.getText().toString();
                if (TextUtils.isEmpty(amtStr)) {
                    showToast("请输入金额");
                    return;
                }
                BigDecimal amt;
                try {
                    amt = new BigDecimal(amtStr);
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("金额格式错误");
                    return;
                }
                mAcqView.toAcquireScan(amt);
                break;
            }
        }
    }
}
