package cn.hey.second.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 教学计划
 * 课程编号
 * pno可与cno组成主键 pno即同一个课程下的计划编号
 * 起始周 结束周 周几 起始节 终止节 上课地点
 * @author Long
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("plan")
public class TeachPlan implements Serializable {
    private String cno;
    private String pno;
    private String teacherName;
    private String startWeek;
    private String EndWeek;
    private String day;
    private String startTime;
    private String endTime;
    private String location;

//    @Override
//    public String toString() {
//        return "["+startWeek+"-"+EndWeek+"周] 星期"+day+" ["+startTime+"-"+endTime+"节] - "+location;
//    }

}
