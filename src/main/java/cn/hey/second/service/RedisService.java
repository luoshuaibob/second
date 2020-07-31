package cn.hey.second.service;

import cn.hey.second.utils.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;


/**
 * Redis 服务类
 *
 * @author Long
 */
@Service
public class RedisService {

    RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 获取对象
     *
     * @param prefix 前缀
     * @param key    key
     * @param clazz  类模板
     * @param <T>    泛型
     * @return Bean
     */
    public <T> T get(String prefix, String key, Class<T> clazz) {
        //对key增加前缀，即可用于分类，也避免key重复
        String realKey = prefix + key;
        String str = (String) redisUtil.get(realKey);
        return str == null?null:stringToBean(str, clazz);
    }

    /**
     * 存储对象
     *
     * @param expire 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public <T> Boolean set(String prefix, String key, Integer expire, T value) {
        String str = beanToString(value);
        if (str == null || str.length() == 0) {
            return false;
        }
        //对key增加前缀，即可用于分类，也避免key重复
        String realKey = prefix + key;
        //获取过期时间
        return redisUtil.set(realKey, str, expire);
    }

    /**
     * 如果不存在就设置
     * @param prefix 前缀
     * @param key key
     * @param value value
     * @param timeout 过期时间
     * @return boolean
     */
    public <T> boolean setIfNotExists(String prefix, String key, T value, Duration timeout){
        String str = beanToString(value);
        if (str==null || str.length()==0){
            return false;
        }
        String realKey = prefix+key;
        return redisUtil.setIfNotExists(realKey,value,timeout);
    }


    /**
     * 删除
     */
    public boolean delete(String prefix, String key) {
        //生成真正的key
        String realKey = prefix + key;
        long ret = redisUtil.del(realKey);
        return ret > 0;
    }

    /**
     * 判断key是否存在
     */
    public <T> boolean exists(String prefix, String key) {
        //生成真正的key
        String realKey = prefix + key;
        return redisUtil.hasKey(realKey);
    }

    /**
     * 增加值
     * Redis Incr 命令将 key 中储存的数字值增加 delta
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作
     */
    public <T> Long incr(String prefix, String key, long delta) {
        //生成真正的key
        String realKey = prefix + key;
        return redisUtil.incr(realKey, delta);
    }

    /**
     * 减少值
     */
    public <T> Long decr(String prefix, String key, long delta) {
        //生成真正的key
        String realKey = prefix + key;
        return redisUtil.decr(realKey, delta);
    }

    /**
     * 把字符串转换成Bean
     *
     * @param str   字符串
     * @param clazz 类模板
     * @param <T>   类型
     * @return Bean
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 把Bean转换成字符串
     *
     * @param value Bean
     * @param <T>   类型
     * @return 字符串
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

}
