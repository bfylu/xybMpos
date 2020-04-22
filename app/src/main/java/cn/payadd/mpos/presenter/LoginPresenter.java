package cn.payadd.mpos.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.payadd.mpos.activity.LoginActivity;
import cn.payadd.mpos.activity.MainActivity;
import cn.payadd.mpos.constant.MPosService;
import cn.payadd.mpos.constant.MessageField;
import cn.payadd.mpos.data.AppGlobalData;
import cn.payadd.mpos.data.UserData;
import cn.payadd.mpos.util.TerminalUtil;
import cn.payadd.mpos.view.ILoginView;

/**
 * Created by zhengzhen.wang on 2016/12/1.
 */

public class LoginPresenter extends BasePresenter {

    private static final int REQ_DOWNLOAD_KEY = 0x1;

    private static final int REQ_SIGN_IN = 0x2;

    private static final int REQ_TOKEN_SIGN_IN = 0x3;

    private ILoginView mLoginView;

    public LoginPresenter(Context mContext, ILoginView mView) {
        super(mContext);
        this.mLoginView = mView;
    }

    /**
     *
     */
    public void downloadKey() {

        Map<String, String> data = new HashMap<>();
        data.put("merCode", mLoginView.getMerCode());
        data.put("phoneNumber1", TerminalUtil.getPhoneNumber(mContext));
        data.put("terminalType", "mobile");
        data.put("systemType", "android");
        data.put("systemVersionName", TerminalUtil.getVersionRelease());
        data.put("systemVersionCode", TerminalUtil.getVersion());
        data.put("imei", TerminalUtil.getImei(mContext));
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("model", TerminalUtil.getModel());
            jsonObj.put("product", TerminalUtil.getProduct());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        data.put("ext1", jsonObj.toString());

        sendMPosServer(REQ_DOWNLOAD_KEY, MPosService.DOWNLOAD_KEY, data);
    }

    /**
     *
     */
    public void signIn() {

        Map<String, String> data = new HashMap<>();
        data.put("username", mLoginView.getUsername());
        data.put("password", mLoginView.getPassword());

        sendMPosServer(REQ_SIGN_IN, MPosService.SIGN_IN, data);
    }

    public void signIn(String loginToken) {

        Map<String, String> data = new HashMap<>();
        data.put("username", mLoginView.getUsername());
        data.put("loginToken", loginToken);

        sendMPosServer(REQ_TOKEN_SIGN_IN, MPosService.SIGN_IN, data, false);
    }

    @Override
    protected void mposCall(int reqCode, int status, Map<String, String> data) {

        boolean flag = mposCallStatusHandler(status, data);
        if (!flag) {
            return;
        }

        switch (reqCode) {
            case REQ_DOWNLOAD_KEY: {
                String mposKey = data.get(MessageField.MPOS_KEY);
                String sessionToken = data.get(MessageField.SESSION_TOKEN);
                AppGlobalData.saveMPosKey(sessionToken, mposKey);
                mLoginView.closeDownloadKeyDialog();
                Toast.makeText(mContext, "下载密钥成功", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQ_SIGN_IN: {

                String loginToken = data.get(MessageField.LOGIN_TOKEN);

                // 保存用户信息
                UserData.saveUsername(mLoginView.getUsername());

                if (mLoginView.isSavePassword()) {
                    UserData.saveLoginToken(loginToken);
                }

                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                mLoginView.finishActivity();
                break;
            }

            case REQ_TOKEN_SIGN_IN: {

                if (status == SUCCESS) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    mLoginView.finishActivity();
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    mLoginView.finishActivity();
                }
                break;
            }
        }

    }
}
