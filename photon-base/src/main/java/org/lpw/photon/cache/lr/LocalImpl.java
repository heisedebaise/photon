package org.lpw.photon.cache.lr;

import org.lpw.photon.scheduler.MinuteJob;
import org.lpw.photon.util.Converter;
import org.lpw.photon.util.Logger;
import org.lpw.photon.util.Numeric;
import org.lpw.photon.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component("photon.cache.lr.local")
public class LocalImpl implements Local, MinuteJob {
    @Inject
    private Validator validator;
    @Inject
    private Converter converter;
    @Inject
    private Numeric numeric;
    @Inject
    private Logger logger;
    @Value("${photon.cache.alive-time:30}")
    private long aliveTime;
    @Value("${photon.cache.max-memory:1g}")
    private String maxMemory;
    private Map<String, Element> map = new ConcurrentHashMap<>();

    @Override
    public void put(Element element) {
        if (element == null || validator.isEmpty(element.getKey()))
            return;

        map.put(element.getKey(), element);
    }

    @Override
    public Element get(String key) {
        if (validator.isEmpty(key))
            return null;

        return map.get(key);
    }

    @Override
    public Element remove(String key) {
        if (validator.isEmpty(key))
            return null;

        return map.remove(key);
    }

    @Override
    public void executeMinuteJob() {
        if (map.isEmpty())
            return;

        List<Element> elements = new ArrayList<>();
        Set<String> obsoletes = new HashSet<>();

        elements.addAll(map.values());
        Collections.sort(elements);

        clearByAliveTime(elements, obsoletes);
        clearByMaxMemory(elements, obsoletes);
        elements.clear();

        if (obsoletes.isEmpty())
            return;

        MemoryUsage usage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        log("开始清理内存", usage);
        if (logger.isInfoEnable())
            logger.info("开始移除{}个本地缓存对象。", obsoletes.size());
        obsoletes.forEach(map::remove);
        if (logger.isInfoEnable())
            logger.info("移除{}个本地缓存对象。", obsoletes.size());
        obsoletes.clear();
        System.gc();
        log("内存清理完毕", usage);
    }

    private void clearByAliveTime(List<Element> elements, Set<String> obsoletes) {
        if (aliveTime < 1)
            return;

        long lastTime = System.currentTimeMillis() - aliveTime * 60 * 1000;
        for (Element element : elements) {
            if (element.getLastVisitedTime() > lastTime)
                break;

            if (!element.isResident())
                obsoletes.add(element.getKey());
        }
    }

    private void clearByMaxMemory(List<Element> elements, Set<String> obsoletes) {
        if (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() < converter.toBitSize(maxMemory) * 3 / 4)
            return;

        for (int i = 0, max = elements.size() / 4; i < max; i++)
            if (!elements.get(i).isResident())
                obsoletes.add(elements.get(i).getKey());
    }

    private void log(String prefix, MemoryUsage usage) {
        if (!logger.isInfoEnable())
            return;

        logger.info("{}[used/max]=[{}/{}={}%]。", prefix, converter.toBitSize(usage.getUsed()),
                converter.toBitSize(usage.getMax()), numeric.toString(100.0D * usage.getUsed() / usage.getMax(), "0.00"));
    }
}
