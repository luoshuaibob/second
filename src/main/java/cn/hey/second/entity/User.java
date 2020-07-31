package cn.hey.second.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 测试
 *
 * @author Long
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    @TableId
    private String sno;

    private String name;
    private String password;
    private Integer sex;
    private Integer grade;
    private Integer major;
    private Integer clazz;
    private String salt;
}
