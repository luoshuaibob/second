package cn.hey.second.service;

import cn.hey.second.mapper.UserMapper;
import cn.hey.second.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户 service
 *
 * @author Long
 */
@Slf4j
@Service
public class UserService {

    private RedisService redisService;
    private UserMapper userMapper;


    /**
     * 自动注入
     */
    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 通过 sno 获取 User
     * @param sno 学号
     * @return 查询到则返回对象，否则返回空
     */
    public User getUserById(String sno) {
        log.info("UserService -->  getUserById()");
        User user;
        // 从Redis中获取
        user = redisService.get("", sno, User.class);
        if (user != null) {
            log.info("getUserById from Redis"+user.toString());
            return user;
        }
        // 从数据库取
        user = userMapper.selectById(sno);
        // 存入缓存
        if (user != null) {
            redisService.set("", sno, 0, user);
            log.info("set user in Redis"+user.toString());
            return user;
        }
        log.info("get user null");
        return null;
    }

}
