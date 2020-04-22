package cn.payadd.mpos.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import cn.payadd.mpos.R;
import cn.payadd.mpos.data.AppGlobalData;
import cn.payadd.mpos.data.UserData;
import cn.payadd.mpos.dialog.DownloadKeyDialog;
import cn.payadd.mpos.presenter.LoginPresenter;
import cn.payadd.mpos.util.ThreadUtil;
import cn.payadd.mpos.view.ILoginView;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String LOG_TAG = "LoginActivity";

    private EditText etUsername, etPassword;
    private CheckBox cbSavePwd;
    private boolean isSavePwd;
    private LoginPresenter loginPresenter;
    private DownloadKeyDialog downloadKeyDialog;

    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    void initView() {

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        cbSavePwd = (CheckBox) findViewById(R.id.cb_save_pwd);

        cbSavePwd.setOnCheckedChangeListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);

    }

    @Override
    void initData() {

        loginPresenter = new LoginPresenter(this, this);

        String mposKey = AppGlobalData.getMPosKey();
        if (TextUtils.isEmpty(mposKey)) {
            downloadKeyDialog = new DownloadKeyDialog();
            downloadKeyDialog.btnDownloadKeyClick = this;
            downloadKeyDialog.show(getFragmentManager(), null);
        }

        String username = UserData.getUsername();
        if (!TextUtils.isEmpty(username)) {
            etUsername.setText(username);
        }
    }

    @Override
    void initPermission() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_download_key: {
                loginPresenter.downloadKey();
                break;
            }
            case R.id.btn_login: {
                loginPresenter.signIn();
                break;
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId()) {
            case R.id.cb_save_pwd: {
                isSavePwd = b;
                break;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        ThreadUtil.close();
    }

    @Override
    public String getMerCode() {
        return downloadKeyDialog.getMerCode();
    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public boolean isSavePassword() {
        return isSavePwd;
    }

    @Override
    public void closeDownloadKeyDialog() {
        if (null != downloadKeyDialog) {
            downloadKeyDialog.dismiss();
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

}
