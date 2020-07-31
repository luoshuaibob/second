package cn.hey.second;

import cn.hey.second.mapper.ClazzMapper;
import cn.hey.second.mapper.TeachPlanMapper;
import cn.hey.second.mapper.UserMapper;
import cn.hey.second.service.ClazzService;
import cn.hey.second.vo.ClazzVO;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SecondApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    TeachPlanMapper teachPlanMapper;
    @Autowired
    ClazzMapper clazzMapper;
    @Autowired
    ClazzService clazzService;

    @Test
    public void userMapperTest(){
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void entityTest(){
        teachPlanMapper.selectList(null).forEach(System.out::println);
        clazzMapper.selectList(null).forEach(System.out::println);
    }
    @Test
    public void clazzServiceTest(){
        List<ClazzVO> classListBySno = clazzService.getClassListBySno("201710211001");
        System.out.println(JSON.toJSONString(classListBySno));

        System.out.println("second ------------------------------------------");

        List<ClazzVO> classListBySno2 = clazzService.getClassListBySno("201710211001");
        System.out.println(JSON.toJSONString(classListBySno2));
    }

}
