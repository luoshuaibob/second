package cn.hey.second.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * choose 实体 的插入和更新时间的自动填充策略配置
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert fill create time");
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("modifiedTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("update fill modified time");
        this.setFieldValByName("modifiedTime",new Date(),metaObject);
    }
}
