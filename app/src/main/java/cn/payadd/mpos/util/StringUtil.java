package cn.payadd.mpos.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {


	/**
	 * 把map转成 key=value&key=value 开形式
	 * @param map
	 * @return
	 */
	public static String link(Map<String, String> map) {
		
		return link(map, null);
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static String linkAndEncode(Map<String, String> map) {
		
		return link(map, "UTF-8");
	}
	
	/**
	 * 
	 * @param map
	 * @param enc URLEncode编码字符
	 * @return
	 */
	public static String link(Map<String, String> map, final String enc) {
		
		StringBuilder sb = new StringBuilder();
		
		try {
			for (String key : map.keySet()) {
				String value = map.get(key);
				if (null != enc && null != value) {
					value = URLEncoder.encode(value, enc);
				}
				sb.append("&").append(key).append("=").append(value);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return sb.length() > 0 ? sb.substring(1) : sb.toString();
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> separate(String str) {
		
		return separate(null, str, null);
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> separateAndURLDecode(String str) {
		
		return separate(null, str, "UTF-8");
	}
	
	/**
	 * 
	 * @param str
	 * @param enc URLDecode解码字符
	 * @return
	 */
	public static Map<String, String> separate(String str, final String enc) {
		
		return separate(null, str, enc);
	}
	
	/**
	 * 
	 * @param map 可以传入map对像，用来排序，如果不传，则创建新的HashMap
	 * @param str
	 * @param enc URLDecode解码字符
	 * @return
	 */
	public static Map<String, String> separate(Map<String, String> map, String str, final String enc) {
		
		if (null == map) {
			map = new HashMap<String, String>();
		}
		
		String[] seq = str.split("&");
		try {
			for (String tmp : seq) {
				String[] split = tmp.split("=");
				if (split.length == 2) {
					if (null != enc) {
						map.put(split[0], URLDecoder.decode(split[1], enc));
					} else {
						map.put(split[0], split[1]);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return map;
	}
}
