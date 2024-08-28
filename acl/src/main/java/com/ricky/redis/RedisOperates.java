package com.ricky.redis;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/11
 * @className RedisOperates
 * @desc redis操作
 */
@Service
public interface RedisOperates<K, V> {

    /**
     * 设置有效时间
     *
     * @param key     redis键
     * @param timeout 有效时间，单位：秒
     * @return true=设置成功 false=设置失败
     */
    boolean expire(final K key, final long timeout);

    /**
     * 设置有效时间
     *
     * @param key     redis键
     * @param timeout 有效时间
     * @param unit    时间单位
     * @return true=设置成功 false=设置失败
     */
    boolean expire(final K key, final long timeout, final TimeUnit unit);

    /**
     * 获取有效时间
     *
     * @param key redis键
     * @return 返回有效时间，单位：秒<br>
     * 返回0代表永久有效
     */
    long getExpire(final K key);

    /**
     * 判断redis键是否存在
     *
     * @param key redis键
     * @return true=存在 false=不存在
     */
    boolean hasKey(final K key);

    /**
     * 删除单个key
     *
     * @param key redis键
     * @return true=删除成功 false=删除失败
     */
    boolean del(final K key);

    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    long del(final Collection<K> keys);

    /**
     * 删除多个key
     *
     * @param key redis键
     * @return 成功删除的个数
     */
    long del(final K... key);

    // 普通对象相关操作

    /**
     * 存入普通对象
     *
     * @param key   redis键
     * @param value 值
     * @return true=放入成功 false=放入失败
     */
    boolean set(final K key, final V value);

    /**
     * 存入普通对象，并设置有效时间
     *
     * @param key     redis键
     * @param value   值
     * @param timeout 有效时间，单位：秒
     * @return true=放入成功 false=放入失败
     */
    boolean set(final K key, final V value, final long timeout);

    /**
     * 获取普通对象
     *
     * @param key redis键
     * @return 对象
     */
    V get(final K key);

    /**
     * 递增
     *
     * @param key   redis键
     * @param delta 增加量，必须大于0
     * @return 返回递增之后的值
     */
    long incr(final K key, final long delta);

    /**
     * 递减
     *
     * @param key   redis键
     * @param delta 减少量，必须大于0
     * @return 返回递键之后的值
     */
    long decr(final K key, final long delta);

    // Hash相关操作

    /**
     * 往hash里存入数据，如果hash表不存在将创建hash表
     *
     * @param key   redis键
     * @param hKey  hash键
     * @param value 值
     * @return true=放入成功 false=放入失败
     */
    boolean hSet(final K key, final K hKey, final V value);

    /**
     * 往hash里存入多个数据，如果hash表不存在将创建hash表
     *
     * @param key    redis键
     * @param values hash键值对
     * @return true=放入成功 false=放入失败
     */
    boolean hSet(final K key, final Map<K, V> values);

    /**
     * 往hash里存入多个数据，如果hash表不存在将创建hash表<br>
     * 设置有效时间
     *
     * @param key     redis键
     * @param hKey    hash键
     * @param value   值
     * @param timeout 有效时间
     * @return true=放入成功 false=放入失败
     */
    boolean hSet(final K key, final K hKey, final V value, final long timeout);

    /**
     * 往hash里存入多个数据
     *
     * @param key    redis键
     * @param values hash键-值
     * @return true=放入成功 false=放入失败
     */
    boolean hMultiSet(final K key, final Map<K, V> values);

    /**
     * 往hash里存入多个数据，并设置有效时间
     *
     * @param key     redis键
     * @param values  hash键-值
     * @param timeout 有效时间
     * @return true=放入成功 false=放入失败
     */
    boolean hMultiSet(final K key, final Map<K, V> values, final long timeout);

    /**
     * 获取hash中的数据
     *
     * @param key  redis键
     * @param hKey hash键
     * @return hash中的对象
     */
    V hGet(final K key, final K hKey);

    /**
     * 获取多个hash中的数据
     *
     * @param key   redis键
     * @param hKeys hash键
     * @return 对象集合
     */
    List<V> hMultiGet(final K key, final Collection<K> hKeys);

    /**
     * 获取多个hash中的数据
     *
     * @param key  redis键
     * @param hKey hash键
     * @return 对象集合
     */
    List<V> hMultiGet(final K key, final K... hKey);

    /**
     * 删除hash表中的值
     *
     * @param key  redis键
     * @param hKey hash键
     */
    void hDel(final K key, final K... hKey);

    /**
     * 判断hash表中是否有该hash键的值
     *
     * @param key  redis键
     * @param hKey hash键
     * @return true=存在 false=不存在
     */
    boolean hHasKey(final K key, final K hKey);

    /**
     * hash递增<br>
     * 如果hash表不存在，则创建hash表，并把新增后的值返回
     *
     * @param key   redis键
     * @param hKey  hash键
     * @param delta 增加值，必须大于0
     * @return 新增后的值
     */
    double hIncr(final K key, final K hKey, double delta);

    /**
     * hash递减<br>
     * 如果hash表不存在，则创建hash表，并把递减后的值返回
     *
     * @param key   redis键
     * @param hKey  hash键
     * @param delta 递减值，必须大于0
     * @return 递减后的值
     */
    double hDecr(final K key, final K hKey, double delta);

    // Set相关操作

    /**
     * 往set中存入数据
     *
     * @param key    redis键
     * @param values 值
     * @return 返回成功存入的个数
     */
    long sSet(final K key, final V... values);

    /**
     * 往set中存入多个数据，并设置有效时间
     *
     * @param key     redis键
     * @param timeout 有效时间，单位：秒
     * @param values  值
     * @return 返回成功存入的个数
     */
    long sSet(final K key, final long timeout, final V... values);

    /**
     * 获取值
     *
     * @param key redis键
     * @return 值集合
     */
    Set<V> sGet(final K key);

    /**
     * 根据value从一个set中查询，是否存在
     *
     * @param key   redis键
     * @param value 值
     * @return true=存在 false-不存在
     */
    boolean sHasKey(final K key, final V value);

    /**
     * 获取set的长度
     *
     * @param key redis键
     * @return set的长度
     */
    long sSize(final K key);

    /**
     * 删除set中的数据
     *
     * @param key    redis键
     * @param values 值
     * @return 返回成功删除的个数
     */
    long sDel(final K key, final V... values);

    // List相关操作

    /**
     * 往list中存入数据
     *
     * @param key   redis键
     * @param value 值
     * @return true=放入成功 false=放入失败
     */
    boolean lSet(final K key, final V value);

    /**
     * 往list中存入数据
     *
     * @param key     redis键
     * @param value   值
     * @param timeout 有效时间，单位：秒
     * @return true=放入成功 false=放入失败
     */
    boolean lSet(final K key, final V value, final long timeout);

    /**
     * 往list中存入多个数据
     *
     * @param key    redis键
     * @param values 值列表
     * @return true=放入成功 false=放入失败
     */
    boolean lSet(final K key, final Collection<V> values);

    /**
     * 往list中存入多个数据
     *
     * @param key     redis键
     * @param values  值列表
     * @param timeout 有效时间，单位：秒
     * @return true=放入成功 false=放入失败
     */
    boolean lSet(final K key, final Collection<V> values, final long timeout);

    /**
     * 根据索引获取list中的值
     *
     * @param key   redis键
     * @param index 索引
     * @return 返回一个值
     */
    V lGet(final K key, final long index);

    /**
     * 从list中获取begin到end之间的元素
     * 0到-1表示所有值
     *
     * @param key   redis键
     * @param begin 开始位置
     * @param end   结束位置
     * @return 元素集合
     */
    List<V> lGet(final K key, final int begin, final int end);

    /**
     * 获取list的长度
     *
     * @param key redis键
     * @return list的长度
     */
    long lSize(final K key);

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   redis键
     * @param index 索引
     * @param value 新值
     * @return true-修改成功 false-修改失败
     */
    boolean lUpdate(final K key, final long index, final V value);

    /**
     * 移除count个值为value的元素
     *
     * @param key   redis键
     * @param count 移除数量
     * @param value 值
     * @return 成功移除的个数
     */
    long lDel(final K key, final long count, final V value);

}
