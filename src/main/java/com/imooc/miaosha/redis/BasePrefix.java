package com.imooc.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {

    @Override
    public int expireSeconds() {
        return 0;
    }

    /**
     * 获取前缀
     * @return
     */
    @Override
    public String getPrefix() {
        return null;
    }
}
