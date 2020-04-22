package cn.payadd.mpos.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zhengzhen.wang on 2016/12/5.
 */

public class AmountUtil {

    public static String format(BigDecimal amt) {

        DecimalFormat df = new DecimalFormat("#########0.00");
        String amtStr = df.format(amt);
        return amtStr;
    }

}
