package cn.payadd.mpos.activity;

import android.content.Intent;
import android.text.TextUtils;

import cn.payadd.mpos.R;
import cn.payadd.mpos.data.UserData;
import cn.payadd.mpos.presenter.LoginPresenter;
import cn.payadd.mpos.view.ILoginView;

/**
 * Created by zhengzhen.wang on 2016/12/9.
 */

public class WelcomeActivity extends BaseActivity implements ILoginView {

    @Override
    int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    void initView() {

    }

    @Override
    void initData() {

        String loginToken = UserData.getLoginToken();

        if (TextUtils.isEmpty(loginToken)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finishActivity();

        } else {

            // token登录
            LoginPresenter loginPresenter = new LoginPresenter(this, this);
            loginPresenter.signIn(loginToken);
        }
    }

    @Override
    void initPermission() {

    }

    @Override
    public String getMerCode() {
        return null;
    }

    @Override
    public String getUsername() {
        return UserData.getUsername();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isSavePassword() {
        return false;
    }

    @Override
    public void closeDownloadKeyDialog() {

    }

    @Override
    public void finishActivity() {
        finish();
    }
}
