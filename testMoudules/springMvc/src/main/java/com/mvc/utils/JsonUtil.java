package com.mvc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.Map;

public class JsonUtil {

    public static String convertToString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty);
    }

    public static Map<String, String> convertToMap(String mapJson) {
        Map<String, String> map = JSON.parseObject(mapJson, new TypeReference<Map<String, String>>() {
        });
        return map;
    }

    public static <T> T StringToObject(String jsonStr, Class<T> clazz) throws JSONException {
        JSONObject json = JSONObject.parseObject(jsonStr);
        T t = JSON.toJavaObject(json, clazz);
        return t;
    }

}
