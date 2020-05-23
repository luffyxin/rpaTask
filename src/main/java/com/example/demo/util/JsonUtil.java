package com.example.demo.util;

/**
 * @Author: dx
 * @Description: 转换json工具
 * @Date: 2020/2/7 0007
 * @Version: 1.0
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import java.util.Date;

public class JsonUtil {
    private static SerializeConfig serializer = new SerializeConfig();

    public JsonUtil() {
    }

    public static <T> T parseObject(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type, new Feature[0]);
    }

    public static <T> T parseObject(String json, Class<T> classOfT) {
        T object = JSON.parseObject(json, classOfT);
        return object;
    }

    public static String toJSON(Object object) {
        serializer.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
        return JSON.toJSONString(object, serializer, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
    }

    public static String toJSONL2String(Object object) {
        serializer.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
        serializer.put(Long.class, ToStringSerializer.instance);
        serializer.put(Long.TYPE, ToStringSerializer.instance);
        return JSON.toJSONString(object, serializer, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
    }

    public static String toJSONL2String(Object object, String datePattern) {
        serializer.put(Date.class, new SimpleDateFormatSerializer(datePattern));
        serializer.put(Long.class, ToStringSerializer.instance);
        serializer.put(Long.TYPE, ToStringSerializer.instance);
        return JSON.toJSONString(object, serializer, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
    }

    public static String toJSON(Object object, String datePattern) {
        serializer.put(Date.class, new SimpleDateFormatSerializer(datePattern));
        return JSON.toJSONString(object, serializer, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
    }
}

