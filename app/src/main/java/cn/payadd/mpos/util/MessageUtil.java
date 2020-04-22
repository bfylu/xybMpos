package cn.payadd.mpos.util;

import android.text.TextUtils;
import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import cn.payadd.mpos.MPosException;
import cn.payadd.mpos.constant.ExceptionCode;
import cn.payadd.mpos.constant.MPosService;
import cn.payadd.mpos.data.AppGlobalData;

/**
 * Created by zhengzhen.wang on 2016/12/2.
 */

public class MessageUtil {

    private static final String DES_ALGORITHM = "DESede";

    private static Key mposKey = null;

    /**
     *
     * @param service
     * @param bodyMap
     * @return
     */
    public static String getMessage(String service, Map<String, String> bodyMap) throws MPosException {

        String body = StringUtil.linkAndEncode(bodyMap);
        Map<String, String> map = new HashMap<>();
        map.put("version", "1.0.0");
        map.put("service", service);
        map.put("platform", "android");
        if (MPosService.DOWNLOAD_KEY.equals(service)) {
            map.put("body", body);
        } else {
            map.put("sessionToken", AppGlobalData.getSessionToken());
            map.put("body", enrypt(body));
        }
        String msg = StringUtil.linkAndEncode(map);
        return msg;
    }

    /**
     *
     * @param bodyMap
     * @return
     */
    public static String getSinature(Map<String, String> bodyMap) {

        return null;
    }

    /**
     *
     * @param sign
     * @param bodyMap
     * @return
     */
    public static boolean verify(String sign, Map<String, String> bodyMap) {

        return true;
    }

    /**
     *
     * @param plaintext
     * @return
     * @throws MPosException
     */
    public static String enrypt(String plaintext) throws MPosException {

        String ciphertext = null;

        try {

            Key key = getMPosKey();
            Cipher c = Cipher.getInstance(key.getAlgorithm());
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] b = c.doFinal(plaintext.getBytes());
            ciphertext = Base64.encodeToString(b, Base64.DEFAULT).replaceAll("\r|\n|\t", "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            new MPosException(ExceptionCode.ENCRYPT_ERROR);

        }

        return ciphertext;
    }

    /**
     *
     * @param ciphertext
     * @return
     * @throws MPosException
     */
    public static String decrypt(String ciphertext) throws MPosException {

        String plaintext = null;

        try {
            Key key = getMPosKey();
            Cipher c = Cipher.getInstance(key.getAlgorithm());
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] b = c.doFinal(Base64.decode(ciphertext, Base64.DEFAULT));
            plaintext = new String(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            new MPosException(ExceptionCode.DECRYPT_ERROR);

        }

        return plaintext;
    }

    /**
     *
     * @return
     */
    public static Key getMPosKey() throws MPosException {

        if (null != mposKey) {
            return mposKey;
        }

        String keyStr = AppGlobalData.getMPosKey();
        if (!TextUtils.isEmpty(keyStr)) {
            byte[] bKey = Hex.stringCoverToByte(keyStr);
            try {
                DESedeKeySpec keySpec = new DESedeKeySpec(bKey);
                mposKey = SecretKeyFactory.getInstance(DES_ALGORITHM).generateSecret(keySpec);
            } catch (InvalidKeyException e) {
                e.printStackTrace();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();

            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

            return mposKey;
        }
        return null;
    }

}
