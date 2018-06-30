package com.imooc.miaosha.redis;

/**
 * 前缀
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
