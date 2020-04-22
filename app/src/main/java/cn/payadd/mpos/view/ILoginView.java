package cn.payadd.mpos.view;

/**
 * Created by zhengzhen.wang on 2016/12/1.
 */

public interface ILoginView extends IView {

    /**
     *
     * @return
     */
    String getMerCode();

    /**
     *
     * @return
     */
    String getUsername();

    /**
     *
     * @return
     */
    String getPassword();

    /**
     *
     * @return
     */
    boolean isSavePassword();

    /**
     *
     */
    void closeDownloadKeyDialog();

    /**
     *
     */
    void finishActivity();

}
