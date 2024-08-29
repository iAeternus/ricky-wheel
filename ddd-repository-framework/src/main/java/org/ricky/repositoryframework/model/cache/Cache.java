package org.ricky.repositoryframework.model.cache;

import jakarta.annotation.Resource;
import lombok.Getter;
import org.ricky.repositoryframework.marker.Aggregate;
import org.ricky.repositoryframework.marker.Identifier;
import org.ricky.repositoryframework.properties.CacheProperties;
import org.springframework.stereotype.Service;


/**
 * @author Ricky
 * @version 1.0
 * @date 2024/7/14
 * @className Cache
 * @desc 缓存对象
 */
@Getter
@Service
public abstract class Cache<T extends Aggregate<ID>, ID extends Identifier> {

    @Resource
    private CacheProperties cacheProperties;

    public abstract T find(ID id);

    public abstract void save(ID id, T aggregate);

    public abstract void remove(ID id);

}
