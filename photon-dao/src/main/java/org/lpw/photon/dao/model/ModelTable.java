package org.lpw.photon.dao.model;

import com.alibaba.fastjson.JSONObject;

import javax.persistence.Column;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

/**
 * Model-Table映射关系表。
 */
public interface ModelTable {
    /**
     * 获得Model类。
     *
     * @return Model类。
     */
    Class<? extends Model> getModelClass();

    /**
     * 设置Model类。
     *
     * @param modelClass Model类。
     */
    void setModelClass(Class<? extends Model> modelClass);

    /**
     * 获取NAME值。
     *
     * @return NAME值。
     */
    String getName();

    /**
     * 设置NAME值。
     *
     * @param name NAME值。
     */
    void setName(String name);

    /**
     * 获取数据源配置。
     *
     * @return 数据源配置。
     */
    String getDataSource();

    /**
     * 设置数据源配置。
     *
     * @param dataSource 数据源配置。
     */
    void setDataSource(String dataSource);

    /**
     * 获得表名称。
     *
     * @return 表名称。
     */
    String getTableName();

    /**
     * 获得表名称。
     *
     * @param date 日期。
     * @return 表名称。
     */
    String getTableName(Date date);

    /**
     * 设置表名称。
     *
     * @param tableName 表名称。
     */
    void setTableName(String tableName);

    /**
     * 设置瞬时表名称。
     *
     * @param tableName 表名称，null或空字符串表示移除。
     */
    void setInstantTableName(String tableName);

    /**
     * 获取内存表名称。
     *
     * @return 内存表名称。
     */
    String getMemoryName();

    /**
     * 设置内存表名称。
     *
     * @param memoryName 内存表名称。
     */
    void setMemoryName(String memoryName);

    /**
     * 获取每日表过期天数。
     *
     * @return 每日表过期天数。
     */
    int getDailyOverdue();

    /**
     * 设置每日表过期天数。
     *
     * @param dailyOverdue 每日表过期天数。
     */
    void setDailyOverdue(int dailyOverdue);

    /**
     * 获得ID对应的表字段名称。
     *
     * @return ID对应的表字段名称。
     */
    String getIdColumnName();

    /**
     * 设置ID对应的表字段名称。
     *
     * @param idColumnName ID对应的表字段名称。
     */
    void setIdColumnName(String idColumnName);

    /**
     * 获取ID是否为UUID。
     *
     * @return ID为UUID则返回true；否则返回false。
     */
    boolean isUuid();

    /**
     * 设置ID是否为UUID。
     *
     * @param uuid ID为UUID则为true；否则为false。
     */
    void setUuid(boolean uuid);

    /**
     * 增加GET方法。
     *
     * @param name   方法名。
     * @param method GET方法。
     */
    void addGetMethod(String name, Method method);

    /**
     * 增加SET方法。
     *
     * @param name   方法名。
     * @param method SET方法。
     */
    void addSetMethod(String name, Method method);

    /**
     * 添加字段映射关系。
     *
     * @param propertyName 属性名。
     * @param column       字段定义。
     */
    void addColumn(String propertyName, Column column);

    /**
     * 获取属性值。
     *
     * @param model Model实例。
     * @param name  属性名称。可以是属性名，也可以是字段名。
     * @param <T>   Model类。
     * @return 属性值。如果不存在则返回null。
     */
    <T extends Model> Object get(T model, String name);

    /**
     * 获取属性值，并转化为属性所定义的类型。
     *
     * @param name  属性名称。
     * @param value 源属性值。
     * @return 目标属性值。
     */
    Object get(String name, Object value);

    /**
     * 设置属性值。
     *
     * @param model Model实例。
     * @param name  属性名称。可以是属性名，也可以是字段名。
     * @param value 属性值。
     * @param <T>   Model类。
     */
    <T extends Model> void set(T model, String name, Object value);

    /**
     * 设置扩展属性值。
     *
     * @param model  Model实例。
     * @param extend 扩展属性集。
     * @param <T>    Model类。
     */
    <T extends Model> void setExtend(T model, JSONObject extend);

    /**
     * 获取字段名称集。
     *
     * @return 字段名称集。
     */
    Set<String> getColumnNames();

    /**
     * 获取属性名称集。
     *
     * @return 属性名称集。
     */
    Set<String> getPropertyNames();

    /**
     * 是否包含属性名。
     *
     * @param propertyName 属性名。
     * @return 如果包含则返回true；否则返回false。
     */
    boolean containsPropertyName(String propertyName);

    /**
     * 获取Jsonable配置对象。
     *
     * @param name 名称。
     * @return Jsonable配置对象；如果不存在则返回null。
     */
    Jsonable getJsonable(String name);

    /**
     * 是否为Native注解的属性。
     *
     * @param name 名称。
     * @return 如果是则返回true；否则返回false。
     */
    boolean isNative(String name);

    /**
     * 获取属性类型。
     *
     * @param name 名称。
     * @return 属性类型；如果不存在则返回null。
     */
    Class<?> getType(String name);

    /**
     * 复制Model属性。
     *
     * @param source    复制源。
     * @param target    目标。
     * @param containId 是否复制ID值。
     * @param <T>       Model类。
     */
    <T extends Model> void copy(T source, T target, boolean containId);

    /**
     * 将Model转化为字符串。
     *
     * @param model Model实例。
     * @param <T>   Model类型。
     * @return 字符串。
     */
    <T extends Model> String toString(T model);
}
