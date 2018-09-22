package com.imooc.miaosha.util;

import cn.hutool.core.lang.UUID;

public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
