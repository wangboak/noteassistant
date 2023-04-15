package com.wangboak.noteassistant.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.key.Jsr310NullKeySerializer;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: wangbo
 * @date: 2023-02-13 18:46
 * @
 **/
@Slf4j
public class JacksonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //支持key为null
        MAPPER.getSerializerProvider().setNullKeySerializer(new Jsr310NullKeySerializer());
    }

    private JacksonUtil() {
    }

    public static String toJsonString(Object object) {
        if (object == null) {
            throw new RuntimeException("object is null, unable to convert");
        }

        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON transformation error");
        }
    }

    public static String toJsonStringQuietly(Object object) {
        if (object == null) {
            log.warn("object is null");
            return null;
        }

        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("JSON transformation error");
        }
        return null;
    }

    public static <T> T toObject(String json, Class<T> cla, String exceptionContent) {
        checkJsonString(json);

        try {
            return MAPPER.readValue(json, cla);
        } catch (IOException e) {
            if ("".equals(exceptionContent) || exceptionContent == null) {
                log.error("json converted to object error.", e);
                throw new RuntimeException("json string cannot be converted to object");
            }
            throw new RuntimeException(exceptionContent);
        }
    }

    public static <T> T toObject(String json, Class<T> cla) {
        return toObject(json, cla, null);
    }

    public static <T> T toObjectQuietly(String json, Class<T> cla) {
        checkJsonStringQuietly(json);

        try {
            return MAPPER.readValue(json, cla);
        } catch (IOException e) {
            log.warn("json string cannot be converted to object");
        }
        return null;
    }

    public static <T> List<T> toArray(String json, Class<T> cla) {
        checkJsonString(json);

        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, cla);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException("json string cannot be converted to array");
        }
    }

    public static <T> List<T> toArrayQuietly(String json, Class<T> cla) {
        checkJsonStringQuietly(json);

        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, cla);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            log.warn("json string cannot be converted to array");
        }
        return null;
    }

    private static void checkJsonStringQuietly(String json) {
        if (json == null) {
            throw new RuntimeException("json is null");
        }
        if ("".equals(json)) {
            throw new RuntimeException("json is empty");
        }
    }

    private static void checkJsonString(String json) {
        if (json == null) {
            log.warn("json is null");
        }
        if ("".equals(json)) {
            log.warn("json is empty");
        }
    }

    public static <T> T toObject(String json, Type type) {
        checkJsonString(json);

        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructType(type));
        } catch (Exception e) {
            throw new RuntimeException("json string cannot be converted to type");
        }
    }

    public static <T> T toObjectQuietly(String json, Type type) {
        checkJsonStringQuietly(json);

        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructType(type));
        } catch (Exception e) {
            log.warn("json string cannot be converted to type");
        }
        return null;
    }

    public static <T> T toObject(InputStream in, Class<T> cla) {
        if (in == null) {
            throw new RuntimeException("InputStream is null");
        }

        try {
            return MAPPER.readValue(in, cla);
        } catch (IOException e) {
            throw new RuntimeException("json string cannot be converted");
        }
    }

    public static <T> T toObjectQuietly(InputStream in, Class<T> cla) {
        if (in == null) {
            log.warn("InputStream is null");
        }

        try {
            return MAPPER.readValue(in, cla);
        } catch (IOException e) {
            log.warn("json string cannot be converted");
        }
        return null;
    }

    public static <T> T toObject(Reader reader, Class<T> cla) {
        if (reader == null) {
            throw new RuntimeException("Reader is null");
        }

        try {
            return MAPPER.readValue(reader, cla);
        } catch (IOException e) {
            throw new RuntimeException("json string cannot be converted");
        }
    }

    public static <T> T toObjectQuietly(Reader reader, Class<T> cla) {
        if (reader == null) {
            log.warn("Reader is null");
        }

        try {
            return MAPPER.readValue(reader, cla);
        } catch (IOException e) {
            log.warn("json string cannot be converted");
        }
        return null;
    }
}
