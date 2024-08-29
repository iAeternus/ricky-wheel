package org.ricky.repositoryframework.model.cache.concrete;

import org.ricky.repositoryframework.marker.Aggregate;
import org.ricky.repositoryframework.marker.Identifier;
import org.ricky.repositoryframework.model.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/14
 * @className MapCache
 * @desc
 */
@Service
public class MapCache<T extends Aggregate<ID>, ID extends Identifier> extends Cache<T, ID> {

    private final Map<ID, T> cacheMap = new HashMap<>();

    @Override
    public synchronized T find(ID id) {
        return cacheMap.get(id);
    }

    @Override
    public synchronized void save(ID id, T aggregate) {
        cacheMap.put(id, aggregate);
    }

    @Override
    public synchronized void remove(ID id) {
        cacheMap.remove(id);
    }

}
