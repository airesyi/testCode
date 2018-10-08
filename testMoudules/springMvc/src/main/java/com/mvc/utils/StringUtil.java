package com.mvc.utils;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil {
    //生成uuid
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //生成零时认证凭证
    public static String genAuthTicket() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if ("".equals(str)) {
            return true;
        }
        if ("null".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    public static String getCheckNum() {
        Integer res = (int) ((Math.random() * 9 + 1) * 100000);
        return res.toString();
    }

    public static boolean matchWithOutNull(String str1, String str2) {
        return matchWithOutNull(str1, str2, false);
    }

    public static boolean matchWithOutNull(String str1, String str2, boolean ignoreCase) {
        if (isEmpty(str1) || isEmpty(str2)) {
            return false;
        }
        if (ignoreCase) {
            return str1.equalsIgnoreCase(str2);
        } else {
            return str1.equals(str2);
        }

    }

    /**
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写
     *                   true:key转化成小写，false:不转化
     * @return
     */
    public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode, boolean keyToLower) {
        if (paraMap == null) {
            return "";
        }
        String buff = "";
        Map<String, String> tmpMap = paraMap;
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                String key = item.getKey();
                String val = item.getValue();
                if (urlEncode) {
                    val = URLEncoder.encode(val, "utf-8");
                }
                if (keyToLower) {
                    buf.append(key.toLowerCase() + "=" + val);
                } else {
                    buf.append(key + "=" + val);
                }
                buf.append("&");
            }
            buff = buf.toString();
            if (buff.equals("") == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    public static String validateMD5(String str) {
        return MD5(str);
    }

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 用于cly短信服务使用
     *
     * @param strSrc,key
     * @return
     */
    public static String MD5Encode(String strSrc, String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes("UTF8"));
            StringBuilder result = new StringBuilder(32);
            byte[] temp;
            temp = md5.digest(key.getBytes("UTF8"));
            for (int i = 0; i < temp.length; i++) {
                result.append(Integer.toHexString(
                        (0x000000ff & temp[i]) | 0xffffff00).substring(6));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 用与加密密码
     */
    public static String MD5Pwd(String username, String userPwd) {
        return MD5(MD5(userPwd) + username);
    }

    /**
     * 助通短信MD5算法
     */
    public static String getMD5(String src) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(src.getBytes());
            byte[] s = m.digest();
            return bintoascii(s);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static String bintoascii(byte[] bySourceByte) {
        int len, i;
        byte tb;
        char high, tmp, low;
        StringBuilder result = new StringBuilder();
        len = bySourceByte.length;
        for (i = 0; i < len; i++) {
            tb = bySourceByte[i];

            tmp = (char) ((tb >>> 4) & 0x000f);
            if (tmp >= 10) {
                high = (char) ('a' + tmp - 10);
            } else {
                high = (char) ('0' + tmp);
            }
            result.append(high);
            tmp = (char) (tb & 0x000f);
            if (tmp >= 10) {
                low = (char) ('a' + tmp - 10);
            } else {
                low = (char) ('0' + tmp);
            }

            result.append(low);
        }
        return result.toString();
    }

    public static String getDateStr() {
        Date tkey = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String str = sdf.format(tkey);
        return str;
    }


}
