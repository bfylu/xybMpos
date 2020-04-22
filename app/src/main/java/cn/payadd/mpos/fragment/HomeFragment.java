package cn.payadd.mpos.fragment;

import android.content.Intent;
import android.view.View;

import cn.payadd.mpos.R;
import cn.payadd.mpos.activity.AcquireActivity;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View v) {

        v.findViewById(R.id.btn_acquire).setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_acquire: {
                Intent intent = new Intent(mContext, AcquireActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
