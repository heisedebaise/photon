package org.lpw.photon.ctrl.context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.lpw.photon.dao.model.Model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * 请求。
 */
public interface Request {
    /**
     * 获取请求ID值。
     * 用于标记Socket、WebSocket请求。
     *
     * @return 请求ID值；如果不存在则返回null。
     */
    String getId();

    /**
     * 获得请求参数值。
     *
     * @param name 参数名称。
     * @return 参数值。
     */
    String get(String name);

    /**
     * 获得请求参数值。
     *
     * @param name         参数名称。
     * @param defaultValue 默认值，null时返回。
     * @return 参数值。
     */
    String get(String name, String defaultValue);

    /**
     * 获得整型请求参数值。
     *
     * @param name 参数名称。
     * @return 整型参数值。
     */
    int getAsInt(String name);

    /**
     * 获得整型请求参数值。
     *
     * @param name         参数名称。
     * @param defaultValue 默认值。
     * @return 整型参数值。
     */
    int getAsInt(String name, int defaultValue);

    /**
     * 获得整型请求参数值。
     *
     * @param name 参数名称。
     * @return 整型参数值。
     */
    long getAsLong(String name);

    /**
     * 获得整型请求参数值。
     *
     * @param name         参数名称。
     * @param defaultValue 默认值。
     * @return 整型参数值。
     */
    long getAsLong(String name, long defaultValue);

    /**
     * 获取浮点型请求参数值。
     *
     * @param name 参数名称。
     * @return 浮点型参数值。
     */
    double getAsDouble(String name);

    /**
     * 获取浮点型请求参数值。
     *
     * @param name         参数名称。
     * @param defaultValue 默认值。
     * @return 浮点型参数值。
     */
    double getAsDouble(String name, double defaultValue);

    /**
     * 获取布尔请求参数值。
     *
     * @param name 参数名。
     * @return 布尔参数值。
     */
    boolean getAsBoolean(String name);

    /**
     * 获得日期型请求参数值。
     *
     * @param name 参数名称。
     * @return 日期型参数值。
     */
    Date getAsDate(String name);

    /**
     * 获得日期型请求参数值。
     *
     * @param name 参数名称。
     * @return 日期型参数值。
     */
    java.sql.Date getAsSqlDate(String name);

    /**
     * 获得日期时间型请求参数值。
     *
     * @param name 参数名称。
     * @return 日期时间型参数值。
     */
    Timestamp getAsTimestamp(String name);

    /**
     * 获得字符串数组请求参数值。
     * 多个值间以逗号分隔。
     *
     * @param name 参数名称。
     * @return 字符串数组。
     */
    String[] getAsArray(String name);

    /**
     * 获取JSON对象请求参数值。
     *
     * @param name 参数名称。
     * @return JSON对象请求参数值。
     */
    JSONObject getAsJsonObject(String name);

    /**
     * 获取JSON数组请求参数值。
     *
     * @param name 参数名称。
     * @return JSON数组请求参数值。
     */
    JSONArray getAsJsonArray(String name);

    /**
     * 获得所有请求参数值对。
     *
     * @return 请求参数值对。
     */
    Map<String, String> getMap();

    /**
     * 获取InputStream中的数据。
     *
     * @return InputStream中的数据；如果不存在则返回空字符串。
     */
    String getFromInputStream();

    /**
     * 将请求参数集保存到Model实例中。
     *
     * @param modelClass Model类。
     * @param <T>        Model类。
     * @return Model实例；如果保存失败则返回null。
     */
    <T extends Model> T setToModel(Class<T> modelClass);

    /**
     * 获取服务器名。
     *
     * @return 服务器名。
     */
    String getServerName();

    /**
     * 获取服务器端口号。
     *
     * @return 服务器端口号。
     */
    int getServerPort();

    /**
     * 获取部署项目名。
     *
     * @return 部署项目名。
     */
    String getContextPath();

    /**
     * 获取请求URL。
     *
     * @return 请求URL。
     */
    String getUrl();

    /**
     * 获取请求URI。
     *
     * @return 请求URI。
     */
    String getUri();

    /**
     * 获取请求方法。
     *
     * @return 请求方法。
     */
    String getMethod();
}
