package cn.payadd.mpos.fragment;

import android.content.Intent;
import android.view.View;

import cn.payadd.mpos.R;
import cn.payadd.mpos.activity.LoginActivity;
import cn.payadd.mpos.data.UserData;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View v) {

        v.findViewById(R.id.btn_sign_out).setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_sign_out: {

                UserData.removeLoginToken();
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                getActivity().finish();
                break;
            }
        }
    }
}
