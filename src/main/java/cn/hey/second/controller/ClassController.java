package cn.hey.second.controller;


import cn.hey.second.entity.TeachPlan;
import cn.hey.second.exception.CodeMsg;
import cn.hey.second.exception.GlobalException;
import cn.hey.second.service.ClazzService;
import cn.hey.second.vo.ClazzVO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * class controller
 * http://localhost:8080/
 * @RestController = @ResponseBody ＋ @Controller
 * @author Long
 */
@Slf4j
@Controller
public class ClassController {

    ClazzService clazzService;
    @Autowired
    private void setClazzService(ClazzService clazzService){
        this.clazzService = clazzService;
    }

    @GetMapping("/{sno}/class/list")
    public String list(Model model, @PathVariable String sno){
        // 判断是否在设置的正选时间段内，不在则添加参数，前端渲染时判断这个参数值，为空则不显示其他的内容
        // 这一步暂时省略
        List<ClazzVO> classListBySno = clazzService.getClassListBySno(sno);
        model.addAttribute("list",classListBySno);
        return "list";
    }

    @GetMapping("/{sno}/class/choose")
    public String choose(@PathVariable String sno){
        return "choose";
    }

    @GetMapping("/{sno}/class/choose/confirm")
    public String confirmChoose(@PathVariable String sno){
        return "choose";
    }

    @GetMapping("/class/list/{cno}")
    @ResponseBody
    public List<TeachPlan> getClassTeachPlan(@PathVariable String cno){
        log.info("getClassTeachPlan ==> cno:"+cno);
        List<TeachPlan> list = clazzService.getTeachPlanListByCno(cno);
        if (list.size() == 0){
            throw new GlobalException(CodeMsg.CLASS_NO_PLAN);
        }
        log.info("getClassTeachPlan ==> return list:"+list);
        return list;
    }

}
