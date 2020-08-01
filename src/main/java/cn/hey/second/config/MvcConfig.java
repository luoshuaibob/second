package cn.hey.second.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC 配置
 * 视图控制器的映射配置

 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 添加映射器，不经过controller
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/home").setViewName("login");
        registry.addViewController("/").setViewName("index");
    }
}
