package org.lpw.photon.dao.dialect;

import org.lpw.photon.bean.BeanFactory;
import org.lpw.photon.bean.ContextRefreshedListener;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository("photon.dao.dialect.factory")
public class DialectFactoryImpl implements DialectFactory, ContextRefreshedListener {
    protected Map<String, Dialect> dialects;

    @Override
    public Dialect get(String name) {
        return dialects.get(name);
    }

    @Override
    public int getContextRefreshedSort() {
        return 2;
    }

    @Override
    public void onContextRefreshed() {
        dialects = new HashMap<>();
        BeanFactory.getBeans(Dialect.class).forEach(dialect -> dialects.put(dialect.getName(), dialect));
    }
}
