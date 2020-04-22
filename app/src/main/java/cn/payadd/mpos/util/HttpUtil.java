package cn.payadd.mpos.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by zhengzhen.wang on 2016/11/4.
 */

public class HttpUtil {


    private static final String CHARSET_NAME = "UTF-8";

    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIMEOUT = 3000;

    /**
     * 数据读取超时时间
     */
    private static final int READ_TIMEOUT = 20000;

    static {
        CertTrust.allowAllSSL();
    }

    /**
     *
     * @param url
     * @param content
     * @return
     */
    public static String post(String url, String content) {

        if (null == url) {
            return null;
        }

        HttpURLConnection httpConn = null;
        OutputStream os = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {

            httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod(METHOD_POST);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(CONNECT_TIMEOUT);
            httpConn.setReadTimeout(READ_TIMEOUT);
            os = httpConn.getOutputStream();
            if (null != content) {
                os.write(content.getBytes(CHARSET_NAME));
            }
            is = httpConn.getInputStream();
            byte[] b = new byte[1024];
            int count = -1;
            baos = new ByteArrayOutputStream();
            while ((count = is.read(b)) != -1) {
                baos.write(b, 0, count);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (SocketTimeoutException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpConn) {
                httpConn.disconnect();
            }
        }

        String result = null;
        try {
            result = baos.toString(CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * GET请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {

        if (null == url) {
            return null;
        }

        HttpURLConnection httpConn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        String result = null;

        try {

            httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod(METHOD_GET);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(false);
            httpConn.setConnectTimeout(CONNECT_TIMEOUT);
            httpConn.setReadTimeout(READ_TIMEOUT);
            is = httpConn.getInputStream();
            byte[] b = new byte[2048];
            int count = -1;
            baos = new ByteArrayOutputStream();
            while ((count = is.read(b)) != -1) {
                baos.write(b, 0, count);
            }

            result = baos.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpConn) {
                httpConn.disconnect();
            }
        }

        return result;
    }
}
