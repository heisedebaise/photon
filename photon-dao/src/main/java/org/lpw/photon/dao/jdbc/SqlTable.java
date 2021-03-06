package org.lpw.photon.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

/**
 * SQL检索结果二维表。
 */
public interface SqlTable {
    /**
     * 获取列名集。
     *
     * @return 列名集。
     */
    String[] getNames();

    /**
     * 获取总行数。
     *
     * @return 总行数。
     */
    int getRowCount();

    /**
     * 获取总列数。
     *
     * @return 总列数。
     */
    int getColumnCount();

    /**
     * 获取数据。
     *
     * @param row    行标，第一行行标为0。
     * @param column 列标，第一列列标为0。
     * @return 数据。
     */
    <T> T get(int row, int column);

    /**
     * 获取数据。
     *
     * @param row        行标，第一行行标为0。
     * @param columnName 列名称。
     * @return 数据。
     */
    <T> T get(int row, String columnName);

    /**
     * 设置数据集。
     *
     * @param rs 数据集。
     * @throws SQLException SQL异常。
     */
    void set(ResultSet rs) throws SQLException;

    /**
     * For Each遍历。
     *
     * @param consumer 处理器。
     */
    void forEach(Consumer<List<Object>> consumer);
}
