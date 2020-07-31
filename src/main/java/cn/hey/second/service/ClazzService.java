package cn.hey.second.service;

import cn.hey.second.entity.Choose;
import cn.hey.second.entity.Clazz;
import cn.hey.second.entity.TeachPlan;
import cn.hey.second.mapper.ChooseMapper;
import cn.hey.second.mapper.ClazzMapper;
import cn.hey.second.mapper.TeachPlanMapper;
import cn.hey.second.vo.ClazzVO;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 关于课程表的 service
 * @author Long
 */
@Slf4j
@Service
public class ClazzService {


    ClazzMapper clazzMapper;
    @Autowired
    private void setClazzMapper(ClazzMapper clazzMapper){
        this.clazzMapper = clazzMapper;
    }

    TeachPlanMapper planMapper;
    @Autowired
    private void setPlanMapper(TeachPlanMapper planMapper){
        this.planMapper = planMapper;
    }

    ChooseMapper chooseMapper;
    @Autowired
    private void setChooseMapper(ChooseMapper chooseMapper){
        this.chooseMapper = chooseMapper;
    }

    RedisService redisService;
    @Autowired
    private void setPlanMapper(RedisService redisService){
        this.redisService = redisService;
    }


    /**
     * 获得 课表 的视图层对象
     * 先从Redis中获取，没有再从数据库中拿，并且存入Redis中
     * 个人的选课信息是私有的，但是课程信息和教学计划是公共的
     * Key 简写 减少空间浪费
     * ch:s - 选课信息  choose:sno
     * pl:c - 详细的教学计划  plan:cno
     * cl:c - 详细的课程信息  class:cno
     */
    public List<ClazzVO> getClassListBySno(String sno){
        log.info("get class list by sno ==> in");
        // 初始化返回的课表列表
        List<ClazzVO> clazzVOList = new LinkedList<>();
        List<Choose> chooseList ;
        // 查询选课表
        chooseList = JSON.parseArray(redisService.get("ch", sno, String.class),Choose.class);
        if (chooseList == null){
            log.info(sno+"redis chooseList null");
            // redis中没有缓存就去数据库中查找
            QueryWrapper<Choose> chooseWrapper = new QueryWrapper<>();
            chooseWrapper.eq("sno",sno);
            chooseList = chooseMapper.selectList(chooseWrapper);
            // 再设置进Redis
            Boolean ch = redisService.set("ch", sno, 10, chooseList);
            log.info(sno+"redis set chooseList "+(ch ?"success":"failed"));
        }
        // 根据选课表的查询结果查询详细情况
        for (Choose choose : chooseList) {
            ClazzVO clazzVO = new ClazzVO();
            // 查找详细的课程信息
            Clazz clazz = redisService.get("cl",choose.getCno(),Clazz.class);
            if (clazz == null){
                log.info(sno+"redis clazz null");
                clazz = clazzMapper.selectById(choose.getCno());
                Boolean cl = redisService.set("cl", choose.getCno(), 60*60*24, clazz);
                log.info(sno+"redis set chooseList "+(cl ?"success":"failed"));
            }
            clazzVO.setClazz(clazz);
            List<TeachPlan> teachPlans = JSON.parseArray(redisService.get("pl",choose.getCno()+choose.getPno(),String.class),TeachPlan.class);
            if (teachPlans == null){
                log.info(sno+"redis teach plan null");
                QueryWrapper<TeachPlan> planWrapper = new QueryWrapper<>();
                planWrapper.eq("cno",choose.getCno());
                planWrapper.eq("pno",choose.getPno());
                teachPlans = planMapper.selectList(planWrapper);
                Boolean pl = redisService.set("pl", choose.getCno() + choose.getPno(), 60*60*24, teachPlans);
                log.info(sno+"redis set chooseList "+(pl ?"success":"failed"));
            }
            clazzVO.setPlanList(teachPlans);
            clazzVOList.add(clazzVO);
        }
        log.info(sno+" ==> "+clazzVOList.toString());
        return clazzVOList;
    }

    public boolean setUserTagIfNotExists(String id,String key,String value){
        redisService.setIfNotExists("",key,value, Duration.ofDays(TimeUnit.DAYS.toDays(1)));
        return false;
    }

    public List<TeachPlan> getTeachPlanListByCno(String cno){
        List<TeachPlan> list = JSON.parseArray(redisService.get("pl",cno,String.class),TeachPlan.class);
        if (list == null){
            QueryWrapper<TeachPlan> planWrapper = new QueryWrapper<>();
            planWrapper.eq("cno",cno);
            list = planMapper.selectList(planWrapper);
            System.out.println(list);
            if (list.size() > 0){
                redisService.set("pl", cno, 60*60*24, list);
            }
        }
        return list;
    }

}
