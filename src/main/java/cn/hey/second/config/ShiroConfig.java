package cn.hey.second.config;


import cn.hey.second.shiro.ShiroRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置类
 * Shiro 三大对象
 *
 * @author Long
 */
@Configuration
public class ShiroConfig {

    /**
     * ShiroFilterFactoryBean
     *
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        // 添加内置过滤器
        /*
         * * 常见过滤器：
         * anon：无需认证（登录）可以访问
         * authc：必须认证才可以访问
         * user:如果使用Remember Me的功能，可以直接访问
         * perms:该资源必须得到资源权限才可以访问
         * role:该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 不用权限 登录页 登录请求 无需拦截
        filterMap.put("/do/Login", "anon");
        filterMap.put("/home", "anon");
        // 需要登录过才能进入 其余任何页面或请求皆需要认证
        filterMap.put("/", "authc");
        filterMap.put("/index", "authc");
        filterMap.put("/*/class/*", "authc");
        filterMap.put("/logout", "authc");

        bean.setFilterChainDefinitionMap(filterMap);
        // 设置登录跳转请求
        bean.setLoginUrl("/home");
        return bean;
    }

    /**
     * DefaultWebSecurityManager
     * Qualifier注解用来传递函数
     *
     * @return DefaultWebSecurityManager
     */
    @Bean(name = "getDefaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") ShiroRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 关联Realm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }


    /**
     * Realm 对象
     */
    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

}
