package cn.hey.second;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Long
 */
@SpringBootApplication
@MapperScan("cn.hey.second.mapper")
public class SecondApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondApplication.class, args);
    }

}
