package cn.payadd.mpos.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by zheng on 16-3-2.
 */
public class HardwareUtils {

    /** Check if this device has a camera */
    public static boolean checkCameraHardware(Context context) {

        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
