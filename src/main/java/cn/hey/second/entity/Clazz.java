package cn.hey.second.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程信息
 * 课程号 学分 学时 课程类别 授课方式 考核方式 任课老师姓名
 * @author Long
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("clazz")
public class Clazz implements Serializable {
    @TableId
    private String cno;
    private String cname;
    private String credit;
    private String clazzHour;
    private String clazzType;
    private String teachType;
    private String testType;

}
