package cn.payadd.mpos.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

public class TerminalUtil {

	/**
	 * 获得IMEI
	 * @param mContext
	 * @return
     */
	public static String getImei(Context mContext){
		String IMEINumber = "";
		if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
			TelephonyManager telephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				IMEINumber = telephonyMgr.getImei();
			} else {
				IMEINumber = telephonyMgr.getDeviceId();
			}
		}
		return IMEINumber;
	}

	/**
	 * 获得手机号码
	 * @param mContext
	 * @return
     */
	public static String getPhoneNumber(Context mContext){



		TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		//String tel = tm.getLine1Number();
		String tel = tm.getNetworkOperator();
		return tel;
	}
	
	public static String getModel(){
		return Build.MODEL;
	}

	public static String getProduct() {
		return Build.PRODUCT;
	}
	
	public static String getVersionRelease(){
		return Build.VERSION.RELEASE;
	}
	
	public static String getVersion(){
		return String.valueOf(Build.VERSION.SDK_INT);
	}

}
