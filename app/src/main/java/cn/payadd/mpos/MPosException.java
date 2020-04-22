package cn.payadd.mpos;

import java.util.HashMap;
import java.util.Map;

import cn.payadd.mpos.constant.ExceptionCode;

/**
 * Created by zhengzhen.wang on 2016/12/2.
 */

public class MPosException extends RuntimeException {

    private static Map<Integer, String> excpMap = new HashMap<>();

    static {
        excpMap.put(ExceptionCode.ENCRYPT_ERROR, "加密错误");
        excpMap.put(ExceptionCode.DECRYPT_ERROR, "解密错误");
    }

    private int excpCode;

    private String excpMsg;

    /**
     *
     * @param excpCode
     */
    public MPosException(int excpCode) {
        this.excpCode = excpCode;
        this.excpMsg = excpMap.get(excpCode);
    }

    /**
     *
     * @param excpCode
     * @param excpMsg
     */
    public MPosException(int excpCode, String excpMsg) {
        this.excpCode = excpCode;
        this.excpMsg = excpMsg;
    }

    @Override
    public String getMessage() {
        return excpMsg;
    }
}
