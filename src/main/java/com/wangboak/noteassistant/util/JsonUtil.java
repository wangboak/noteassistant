package com.wangboak.noteassistant.util;

import java.util.List;

/**
 *
 * @author: wangbo
 * @date: 2023-04-15 19:13
 * @
 **/
public class JsonUtil {

    private JsonUtil() {
    }

    /**
     * 序列化为 JSON 字符串。
     *
     * @param obj, obj 为null时，返回null。序列化失败时，也返回null。
     * @return
     */
    public static String toJson(Object obj) {
        return JacksonUtil.toJsonStringQuietly(obj);
    }

    /**
     * 反序列化。当反序列化失败是，会抛出异常。
     * @param json
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T parse(String json, Class<T> clazz) {
        return JacksonUtil.toObject(json, clazz);
    }

    /**
     * 反序列化。当反序列化失败时，会抛出异常。
     * @param json
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JacksonUtil.toArray(json, clazz);
    }


}
