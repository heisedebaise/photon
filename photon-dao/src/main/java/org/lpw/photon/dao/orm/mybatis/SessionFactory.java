package org.lpw.photon.dao.orm.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.lpw.photon.dao.ConnectionFactory;

/**
 * @author lpw
 */
public interface SessionFactory extends ConnectionFactory<SqlSessionFactory> {
}
