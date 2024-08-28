package com.ricky.redis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/11
 * @className RedisACL
 * @desc redis防腐层
 */
@Slf4j
@Service
public class RedisACL implements RedisOperates<String, Object> {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean expire(String key, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit unit) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, unit);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getExpire(String key) {
        try {
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (expire == null) {
                throw new RuntimeException("getExpire return null, key=" + key);
            }
            return expire;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean del(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.delete(key));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long del(Collection<String> keys) {
        try {
            Long result = redisTemplate.delete(keys);
            return result == null ? 0 : result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public long del(String... key) {
        if (key == null || key.length == 0) {
            throw new RuntimeException("Key is empty");
        }

        if (key.length == 1) {
            return Boolean.TRUE.equals(redisTemplate.delete(key[0])) ? 1 : 0;
        } else {
            Long result = redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            return result == null ? 0 : result;
        }
    }

    @Override
    public boolean set(String key, Object value) {
        // TODO
        return false;
    }

    @Override
    public boolean set(String key, Object value, long timeout) {
        return false;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public long incr(String key, long delta) {
        return 0;
    }

    @Override
    public long decr(String key, long delta) {
        return 0;
    }

    @Override
    public boolean hSet(String key, String hKey, Object value) {
        return false;
    }

    @Override
    public boolean hSet(String key, Map<String, Object> values) {
        return false;
    }

    @Override
    public boolean hSet(String key, String hKey, Object value, long timeout) {
        return false;
    }

    @Override
    public boolean hMultiSet(String key, Map<String, Object> values) {
        return false;
    }

    @Override
    public boolean hMultiSet(String key, Map<String, Object> values, long timeout) {
        return false;
    }

    @Override
    public Object hGet(String key, String hKey) {
        return null;
    }

    @Override
    public List<Object> hMultiGet(String key, Collection<String> hKeys) {
        return null;
    }

    @Override
    public List<Object> hMultiGet(String key, String... hKey) {
        return null;
    }

    @Override
    public void hDel(String key, String... hKey) {

    }

    @Override
    public boolean hHasKey(String key, String hKey) {
        return false;
    }

    @Override
    public double hIncr(String key, String hKey, double delta) {
        return 0;
    }

    @Override
    public double hDecr(String key, String hKey, double delta) {
        return 0;
    }

    @Override
    public long sSet(String key, Object... values) {
        return 0;
    }

    @Override
    public long sSet(String key, long timeout, Object... values) {
        return 0;
    }

    @Override
    public Set<Object> sGet(String key) {
        return null;
    }

    @Override
    public boolean sHasKey(String key, Object value) {
        return false;
    }

    @Override
    public long sSize(String key) {
        return 0;
    }

    @Override
    public long sDel(String key, Object... values) {
        return 0;
    }

    @Override
    public boolean lSet(String key, Object value) {
        return false;
    }

    @Override
    public boolean lSet(String key, Object value, long timeout) {
        return false;
    }

    @Override
    public boolean lSet(String key, Collection<Object> values) {
        return false;
    }

    @Override
    public boolean lSet(String key, Collection<Object> values, long timeout) {
        return false;
    }

    @Override
    public Object lGet(String key, long index) {
        return null;
    }

    @Override
    public List<Object> lGet(String key, int begin, int end) {
        return null;
    }

    @Override
    public long lSize(String key) {
        return 0;
    }

    @Override
    public boolean lUpdate(String key, long index, Object value) {
        return false;
    }

    @Override
    public long lDel(String key, long count, Object value) {
        return 0;
    }
}
