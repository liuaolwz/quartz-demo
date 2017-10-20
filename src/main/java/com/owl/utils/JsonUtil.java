package com.owl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtil {
  private static ObjectMapper mapper = new ObjectMapper();

  public static ObjectMapper getMapper() {
    return mapper;
  }

  /**
   * json serialization.
   *
   * @param obj 要进行序列化的对象
   * @return 返回序列化之后的字符串
   */
  public static String dumps(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      log.error("序列化json异常", e);
      throw new RuntimeException("序列化json异常");
    }
  }

  /**
   * json deserialization.
   *
   * @param jsonStr 序列化字符串
   * @param cls     指定反序列化之后的类信息
   * @return 返回反序列化之后的实例对象
   */
  public static <T> T loads(final String jsonStr, Class<T> cls) {
    T object = null;
    try {
      object = mapper.readValue(jsonStr, cls);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("反序列化异常{}", e);
      throw new RuntimeException("反序列化异常");
    }
    return object;
  }

  public static <T> T loads(final String jsonStr, Class<T> parametrized, Class<?>... parameterClasses) {
    T object = null;
    JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    try {
      object = mapper.readValue(jsonStr, javaType);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("反序列化异常", e);
      throw new RuntimeException("反序列化异常");
    }
    return object;
  }
}
