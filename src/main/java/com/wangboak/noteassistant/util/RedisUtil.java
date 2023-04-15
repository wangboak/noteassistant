package com.wangboak.noteassistant.util;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Redis 封装工具类。
 * 参考：https://www.jianshu.com/p/254652e2e583
 *
 * @description: RedisClient
 * @author: wangboak
 **/
@Component
@Slf4j
public class RedisUtil {

    @Resource
    private StringRedisTemplate redisTemplateInner;

    private static StringRedisTemplate redisTemplate;

    @PostConstruct
    private void init() {
        //目的是提供 静态方法。
        redisTemplate = redisTemplateInner;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间（秒）
     */
    public static void expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("redis expire error.", e);
        }
    }

    /**
     * 根据key 获取过期时间.
     *
     * @param key 键 不能为null
     * @return 时间(秒) -1：key 存在但没有设置剩余生存时间时。-2：key 不存在。否则，以秒为单位，返回 key 的剩余生存时间。
     */
    public static long getExpire(String key) {
        if (null == key) {
            return -2;
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis hasKey error.", e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static <T> T get(String key, Class<T> clazz) {
        String v = get(key);
        if (StringUtils.isBlank(v)) {
            return null;
        }
        return JsonUtil.parse(v, clazz);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis set error.", e);
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param timeOnSeconds  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, String value, long timeOnSeconds) {
        try {
            if (timeOnSeconds > 0) {
                redisTemplate.opsForValue().set(key, value, timeOnSeconds, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("redis set error.", e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key 键
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key 键
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
}
