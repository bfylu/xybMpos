package cn.payadd.mpos.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import cn.payadd.mpos.config.AppGlobalConfig;
import cn.payadd.mpos.constant.MessageField;
import cn.payadd.mpos.constant.RespCodeStatus;
import cn.payadd.mpos.util.HttpUtil;
import cn.payadd.mpos.util.MessageUtil;
import cn.payadd.mpos.util.StringUtil;
import cn.payadd.mpos.util.ThreadUtil;

/**
 * Created by zhengzhen.wang on 2016/12/1.
 */

public abstract class BasePresenter {

    private static final String LOG_TAG = "BasePresenter";

    protected static final int SUCCESS = 0x1;
    protected static final int FAILURE = 0x2;
    protected static final int EXCEPTION = 0x3;
    protected static final int NETWORK_ERROR = 0x4;

    protected Context mContext;

    /**
     * 是否自动处理状态
     */
    private boolean isMPosCallStatusHandler;

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 发送MPOS服务器
     * @param reqCode
     * @param service
     * @param data
     * @param isCallSatusHandler
     */
    public void sendMPosServer(int reqCode, String service, Map<String, String> data, boolean isCallSatusHandler) {

        this.isMPosCallStatusHandler = isCallSatusHandler;

        final String msg = MessageUtil.getMessage(service, data);
        FutureTask<String> ft = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {

                Log.d(LOG_TAG, "send -> " + msg);
                String result = HttpUtil.post(AppGlobalConfig.MPOS_SERVER, msg);
                Log.d(LOG_TAG, "result -> " + result);
                return result;
            }
        });
        try {
            ThreadUtil.exec(ft);
            String result = ft.get();
            if (null == result) {
                mposCall(reqCode, NETWORK_ERROR, null);
            } else {
                Map<String, String> rData = StringUtil.separateAndURLDecode(result);
                String respCode = rData.get(MessageField.RESP_CODE);
                if (RespCodeStatus.SUCCESS.equals(respCode)) {
                    mposCall(reqCode, SUCCESS, rData);
                } else {
                    mposCall(reqCode, FAILURE, rData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mposCall(reqCode, EXCEPTION, null);
        }
    }

    /**
     *
     * @param reqCode
     * @param service
     * @param data
     */
    public void sendMPosServer(int reqCode, String service, Map<String, String> data) {

        sendMPosServer(reqCode, service, data, true);
    }

    /**
     *
     * @param reqCode
     * @param status
     * @param data
     */
    protected abstract void mposCall(int reqCode, int status, Map<String, String> data);

    /**
     * mpos执行回调前可调用的状态处理方法，可用来处理异常
     * @param status
     * @return
     */
    protected boolean mposCallStatusHandler(int status, Map<String, String> data) {

        if (status == SUCCESS || ! isMPosCallStatusHandler) {
            return true;
        }

        String msg = null;
        if (null == data) {
            msg = "没有返回数据";
        } else {
            msg = data.get(MessageField.RESP_DESC);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示").setMessage(msg).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

        return false;
    }

}
