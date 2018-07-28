package com.imooc.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;


    private String prefix;

    /*
     *0代表永不过期
     */
    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 0 代表永远不过期
     * @return
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * 获取前缀
     * @return
     */
    @Override
    public String getPrefix() {
        return getClass().getSimpleName()+":"+prefix;
    }
}
