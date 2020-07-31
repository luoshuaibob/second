package cn.hey.second.mapper;

import cn.hey.second.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * Mybatis-plus 自动实现接口
 * Repository 代表持久层
 * @author Long
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


}
