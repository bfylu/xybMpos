package cn.payadd.mpos.fragment;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.payadd.mpos.R;
import cn.payadd.mpos.util.HardwareUtils;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public class AcquireScanFragment extends BaseFragment implements SurfaceHolder.Callback, Camera.PictureCallback {

    public static final String KEY_PARAM_AMOUNT = "amount";

    private static final String LOG_TAG = "AcquireScanFragment";

    private TextView tvAcquireAmount;
    private SurfaceView svScanQrcode;
    private Camera camera;
    private Timer timer;

    private volatile boolean captureFlag;
    private Reader reader;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_acquire_scan;
    }

    @Override
    public void initView(View v) {

        Bundle args = getArguments();

        tvAcquireAmount = (TextView) v.findViewById(R.id.tv_acquire_amount);
        tvAcquireAmount.setText(args.getString(KEY_PARAM_AMOUNT));

        svScanQrcode = (SurfaceView) v.findViewById(R.id.sv_scan_qrcode);
        svScanQrcode.setFocusable(true);
//        svScanQrcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                camera.cancelAutoFocus();
//            }
//        });
        SurfaceHolder holder = svScanQrcode.getHolder();
        holder.setKeepScreenOn(true);
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        if (HardwareUtils.checkCameraHardware(mContext)) {
            if (null == camera) {
                try {
                    camera = Camera.open();
                } catch (Exception e) {
                    showShortToast("相机错误");
                    return;
                }

                Camera.Parameters params = camera.getParameters();
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//              params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                camera.setParameters(params);

                camera.startPreview();
                camera.cancelAutoFocus();

                startCapture();
            }

            try {
                camera.setDisplayOrientation(90);
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        if (null == surfaceHolder.getSurface()){
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        //将图片保存
//        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//        File f = new File(CommonUtils.getSDCardPath(), "photo.jpg");
//        ImageUtils.compress(bitmap, 80, f);
    }

    @Override
    public void onDestroy() {

        stopCapture();
        super.onDestroy();
    }

    public void startCapture() {
        if (null == timer) {
            timer = new Timer();
        }

        captureFlag = true;

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                Log.d(LOG_TAG, "exec TimerTask");

                if (null != camera) {
                    while (captureFlag) {
                        camera.setOneShotPreviewCallback(previewCallback);
                    }
                    try {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.d(LOG_TAG, "finish TimerTask");
                }
            }
        }, 0, 100);
    }

    public void stopCapture() {

        captureFlag = false;
        if (null != timer) {
            timer.cancel();
            timer.purge();
        }
    }

    /**
     *  自动对焦后输出图片
     */
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {

        @Override
        public synchronized void onPreviewFrame(byte[] data, Camera arg1) {

            if (! captureFlag) {
                return;
            }

            try {

                int previewWidth = camera.getParameters().getPreviewSize().width;
                int previewHeight = camera.getParameters().getPreviewSize().height;

                PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                        data, previewWidth, previewHeight, 0, 0, previewWidth,
                        previewHeight, false);

                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                if (null == reader) {
                    reader = new QRCodeReader();
                }

                Result result = reader.decode(bitmap);
                String content = result.getText();

                Log.d(LOG_TAG, "scan content -> " + content);
                stopCapture();

            } catch (Exception e) {

            }
        }
    };
}
