package com.we.piccategory.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
     * String -> MD5
     */
    public static String getMD5(String val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();// 加密
        return getString(m);
    }

    // bArray -> String
    private static String getString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<b.length; i++)
            hexString.append(Integer.toHexString(0xFF & b[i]));
        return hexString.toString();
    }
}
