package com.abc1236.ms.constant.cache;

import java.time.Duration;

/**
 * 所有缓存名称的集合
 *
 * @author fengshuonan
 * @date 2017-04-24 21:56
 */
public interface Cache {

    /**
     * 常量缓存
     */
    String APPLICATION = "APPLICATION";
    String CONSTANT = "CONSTANT";
    String SESSION = "SESSION";
    String MINUTE = "MINUTE";
    String HOUR = "HOUR";
    String DAY = "DAY";


    public static Duration timout(String key) {
        Duration duration = Duration.ofSeconds(60);
        if (Cache.CONSTANT.equals(key)) {
            duration = Duration.ofSeconds(2592000);
        } else if (Cache.SESSION.equals(key)) {
            duration = Duration.ofSeconds(3600);
        } else if (Cache.APPLICATION.equals(key)) {
            duration = Duration.ofSeconds(1800);
        } else if (Cache.DAY.equals(key)) {
            duration = Duration.ofSeconds(86400);
        } else if (Cache.HOUR.equals(key)) {
            duration = Duration.ofSeconds(3600);
        } else if (Cache.MINUTE.equals(key)) {
            duration = Duration.ofSeconds(60);
        }
        return duration;
    }
}
