package com.imooc.miaosha.redis;

public class MiaoShaUserKey extends BasePrefix {
    private static final int TOOKEN_EXPIRE = 3600*24*2;
    private MiaoShaUserKey(int expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static MiaoShaUserKey token = new MiaoShaUserKey(TOOKEN_EXPIRE,"tk");


}
