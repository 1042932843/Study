/*
 * Copyright © 2015 珠海云集软件科技有限公司.
 * Website：http://www.YunJi123.com
 * Mail：dev@yunji123.com
 * Tel：+86-0756-8605060
 * QQ：340022641(dove)
 * Author：dove
 */

package com.dy.ustudyonline.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Name: CrashHandler
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-11-22 14:50
 */
public final class StringUtil {

    public static String md5(String s) {
        if (s == null) return null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(s.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int val = b & 0xFF;
                if (val < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    // 匹配md5
    public static boolean matchesMd5(String s) {
        return s != null && s.matches("^[a-f0-9A-F]{32}$");
    }

    // 登录密码 6-20位字符
    public static boolean matchesPassword(String s) {
        return s != null && s.matches("^.{6,20}$");
    }

    // 匹配网址
    public static boolean matchesHttp(String s) {
        return s != null && s.matches("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?");
    }

    // 匹配手机号码
    public static boolean matchesPhone(String s) {
        return s != null && s.matches("^1[34578]\\d{9}$");
    }

    // 短信验证码 4位数字
    public static boolean matchesCode(String s) {
        return s != null && s.matches("^\\d{4}$");
    }

    // 匹配中文
    public static boolean matchesChinese(String s) {
        return s != null && s.matches("^.*[\\u4e00-\\u9fa5]+.*$");
    }

    // 匹配身份证
    public static boolean matchesIdCard(String s) {
        return s != null && s.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
    }

    // 匹配邮箱
    public static boolean matchesEmail(String s) {
        return s != null && s.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    }

    // 匹配邮编
    public static boolean matchesZipcode(String s) {
        return s != null && s.matches("^\\d{6}$");
    }

    // 匹配数字
    public static boolean matchesNumber(String s) {
        return s != null && s.matches("^\\d+$");
    }
    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            throw new IllegalArgumentException("Bank card code must be number!");
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
    // 加密后解密
    public static String JM(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String k = new String(a);
        return k;
    }
}
