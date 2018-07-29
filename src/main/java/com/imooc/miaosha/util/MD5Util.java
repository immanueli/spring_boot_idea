package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5Util
 */
public class MD5Util {
    private static final String salt = "1a2b3c4d";


    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }


    /**
     * 用户输入的密码加密
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String newInputPass = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(newInputPass);
    }

    /**
     * 用于存放在数据库中的密码
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass,String salt) {
        String dbInputPass = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(dbInputPass);
    }

    public static String inputPassToDBPass(String input,String saltDB){
        String formPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
        System.out.println(inputPassToFormPass("123456"));
    }
}
