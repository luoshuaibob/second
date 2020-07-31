package cn.hey.second.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 选课表
 * @author Long
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choose {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String sno;

    private String cno;
    private String pno;
    @Version
    private Integer version;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;
}
