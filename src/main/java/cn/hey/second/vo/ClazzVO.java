package cn.hey.second.vo;

import cn.hey.second.entity.Clazz;
import cn.hey.second.entity.TeachPlan;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

/**
 * class 课表的视图层对象
 * 结合了entity Clazz和 entity TeachPlan
 * toString 输出 json 字符串
 * @author Long
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzVO {
    @JSONField(ordinal = 1)
    private String cno;
    private String cname;
    private String credit;
    private String clazzHour;
    private String clazzType;
    private String teachType;
    private String testType;
    private List<TeachPlan> planList;


    public void setClazz(Clazz clazz) {
        this.cno = clazz.getCno();
        this.credit = clazz.getCredit();
        this.clazzHour = clazz.getClazzHour();
        this.clazzType = clazz.getClazzType();
        this.teachType = clazz.getTeachType();
        this.testType = clazz.getTestType();
        this.cname = clazz.getCname();
    }

    public Clazz returnClazzEntity(){
        return new Clazz(cno,cname,credit,clazzHour,clazzType,teachType,testType);
    }

    public void addTeachPlan(TeachPlan plan){
        this.planList.add(plan);
    }

}
