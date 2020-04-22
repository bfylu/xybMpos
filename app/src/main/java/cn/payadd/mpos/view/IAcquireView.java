package cn.payadd.mpos.view;

import java.math.BigDecimal;

/**
 * Created by zhengzhen.wang on 2016/12/3.
 */

public interface IAcquireView {

    /**
     * 收款
     * @param amt
     */
    void toAcquireScan(BigDecimal amt);

}
