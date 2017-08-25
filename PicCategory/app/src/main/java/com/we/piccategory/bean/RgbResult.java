package com.we.piccategory.bean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.piccategory.util.JsonUtils;

import java.util.List;


public class RgbResult {

    public static final Integer STATUS_OK = 200;
    public static final Integer STATUS_FAIL = 404;
    public static final Integer STATUS_ERROR = 500;
    public static final Integer STATUS_LOGINOUT = 300;
    public static final Integer STATUS_NOLOGIN = 100;
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态，200请求成功、500服务器内部错误、404请求失败，
    private Integer status;

    // 响应中的数据
    private Object data;

    public static RgbResult build(Integer status, Object data) {
        return new RgbResult(status, data);
    }

    public static RgbResult ok(Object data) {
        return new RgbResult(data);
    }

    public static RgbResult ok() {
        return new RgbResult(null);
    }

    public RgbResult() {

    }

    public static RgbResult build(Integer status) {
        return new RgbResult(status, null);
    }

    public RgbResult(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }

    public RgbResult(Object data) {
        this.status = STATUS_OK;
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为TaotaoResult对象
     *
     * @param jsonData json数据
     * @param clazz    TaotaoResult中的object类型
     * @return
     */
    public static RgbResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, RgbResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = data.asText();
                }
            }
            return build(jsonNode.get("status").intValue(), obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static RgbResult format(String json) {
        try {
            return MAPPER.readValue(json, RgbResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz    集合中的类型
     * @return
     */
    public static RgbResult formatToList(String jsonData, Class<?> clazz) {
        JsonUtils.jsonToList(jsonData, clazz);
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
