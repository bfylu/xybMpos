package cn.payadd.mpos.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import cn.payadd.mpos.R;

/**
 * Created by zhengzhen.wang on 2016/12/1.
 */

public class DownloadKeyDialog extends DialogFragment implements View.OnClickListener {

    public View.OnClickListener btnDownloadKeyClick;

    private EditText etMerCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.dialog_downlod_key, null);
        etMerCode = (EditText) v.findViewById(R.id.et_mer_code);
        v.findViewById(R.id.btn_download_key).setOnClickListener(this);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("下载密钥");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_download_key: {
                if (null != btnDownloadKeyClick) {
                    btnDownloadKeyClick.onClick(view);
                }
                break;
            }
        }
//        getDialog().dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);

        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public String getMerCode() {

        return etMerCode.getText().toString();
    }
}
