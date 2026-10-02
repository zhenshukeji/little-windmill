package com.zhenshu.system.cache;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.zhenshu.common.constant.Constants;
import com.zhenshu.common.core.redis.RedisUtils;
import com.zhenshu.common.domain.PageEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author jing
 * @version 1.0
 * @desc 缓存以及业务类
 * @date 2020/6/11 0011 13:50
 * <p>
 * 不传递time、time为Null: 一天
 * time等于小于0: 永不过期
 * time大于0: 设置指定的过期时间
 */
@Component
public class CachesManages {
    @Resource
    protected RedisUtils cache;
    @Resource
    protected CacheKey cacheKeyManager;
    @Resource(name = "stringRedisTemplate")
    protected StringRedisTemplate redisTemplate;

    private static final Long EVER = -1L;

    /**
     * 获取一天的秒数，加上一小时的随机时间，避免缓存雪崩
     *
     * @return 缓存过期时间, 单位秒
     */
    public long getDayTime() {
        return Constants.DAY_TIME_SECOND + RandomUtil.randomInt(0, Constants.ONE_HOUR);
    }

    /**
     * 获取Hash类型分页key
     *
     * @param pageEntity 分页入参
     * @return 分页key
     */
    public String getPageHashKey(PageEntity pageEntity) {
        return String.format("%d-%d", pageEntity.getPageNum(), pageEntity.getPageSize());
    }

    /**
     * 保存HashKey, 同时设置过期时间, 默认单位秒;
     *
     * @param cacheKey 缓存Key
     * @param hashKey  hashKey
     * @param data     数据
     * @param time     过期时间
     */
    protected List<Object> hSet(String cacheKey, String hashKey, String data, long time) {
        return this.hSet(cacheKey, hashKey, data, time, TimeUnit.SECONDS);
    }

    /**
     * 保存HashKey, 同时设置过期时间; 支持毫秒和秒
     *
     * @param cacheKey 缓存Key
     * @param hashKey  hashKey
     * @param data     数据
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return 结果
     */
    protected List<Object> hSet(String cacheKey, String hashKey, String data, long time, TimeUnit timeUnit) {
        return this.pipeline(connection -> {
            // 设置缓存
            connection.hSet(cacheKey, hashKey, data);
            // 设置过期时间
            if (timeUnit == TimeUnit.SECONDS) {
                connection.expire(cacheKey, time);
            } else if (timeUnit == TimeUnit.MILLISECONDS) {
                connection.pExpire(cacheKey, time);
            }
        });
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param time     过期时间
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> T get(String cacheKey, Supplier<T> supplier, Class<T> tClass, Long time) {
        String data = cache.get(cacheKey);
        if (org.apache.commons.lang3.StringUtils.isEmpty(data)) {
            return this.set(cacheKey, supplier, time);
        }
        return this.parseObject(data, tClass);
    }

    /**
     * 设置数据到redis中, 当过期时间为null或小于0时, 将设置永不过期的缓存
     * 过期时间为null: 一天的过期时间
     * 过期时间等于小于0: 永不过期
     * 过期时间大于0: 设置指定的过期时间
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param time     过期时间
     * @param <T>      泛型
     * @return 缓存对象
     */
    protected <T> T set(String cacheKey, Supplier<T> supplier, Long time) {
        T t = supplier.get();
        if (time == null) {
            cache.set(cacheKey, this.toJSONString(t), getDayTime());
        } else if (time <= Constants.ZERO) {
            cache.set(cacheKey, this.toJSONString(t));
        } else {
            cache.set(cacheKey, this.toJSONString(t), time);
        }
        return t;
    }

    /**
     * 缓存获取数据, 时间一天
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> T get(String cacheKey, Supplier<T> supplier, Class<T> tClass) {
        return this.get(cacheKey, supplier, tClass, null);
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param time     缓存的过期时间
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> getList(String cacheKey, Supplier<List<T>> supplier, Class<T> tClass, Long time) {
        String data = cache.get(cacheKey);
        if (org.apache.commons.lang3.StringUtils.isEmpty(data)) {
            return this.set(cacheKey, supplier, time);
        }
        return this.parseArray(data, tClass);
    }

    /**
     * 缓存获取数据, 时间一天
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> getList(String cacheKey, Supplier<List<T>> supplier, Class<T> tClass) {
        return this.getList(cacheKey, supplier, tClass, null);
    }

    /**
     * 缓存获取数据, 时间一天
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> page(String cacheKey, PageEntity pageEntity, Supplier<List<T>> supplier, Class<T> tClass) {
        return this.page(cacheKey, pageEntity, supplier, tClass, this.getDayTime());
    }

    /**
     * 缓存获取数据
     *
     * @param cacheKey 缓存key
     * @param supplier 获取数据的函数式接口
     * @param tClass   转换的class对象
     * @param time     缓存的过期时间
     * @param <T>      泛型
     * @return 结果
     */
    protected <T> List<T> page(String cacheKey, PageEntity pageEntity, Supplier<List<T>> supplier, Class<T> tClass, long time) {
        String hashKey = this.getPageHashKey(pageEntity);
        String data = (String) cache.hGet(cacheKey, hashKey);
        if (StringUtils.isEmpty(data)) {
            List<T> list = supplier.get();
            this.hSet(cacheKey, hashKey, this.toJSONString(list), time);
            return list;
        }
        return this.parseArray(data, tClass);
    }

    /**
     * 管道操作
     *
     * @param consumer redis操作
     * @return 结果
     */
    public List<Object> pipeline(Consumer<StringRedisConnection> consumer) {
        return redisTemplate.executePipelined((RedisCallback<?>) redisConnection -> {
            StringRedisConnection connection = (StringRedisConnection) redisConnection;
            connection.openPipeline();
            consumer.accept(connection);
            return null;
        });
    }

    /**
     * 对象转JSON字符串
     *
     * @param obj 对象
     * @return 结果
     */
    public String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * JSON字符串转对象
     *
     * @param json   数据
     * @param tClass class对象
     * @return 结果
     */
    public <T> T parseObject(String json, Class<T> tClass) {
        return JSON.parseObject(json, tClass);
    }

    /**
     * JSON字符串转对象
     *
     * @param json   数据
     * @param tClass class对象
     * @return 结果
     */
    public <T> List<T> parseArray(String json, Class<T> tClass) {
        return JSON.parseArray(json, tClass);
    }
}

