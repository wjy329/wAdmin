package com.wjy329.wshiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.wjy329.wshiro.service.PermissionService;
import com.wjy329.wshiro.service.RolePermService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;
/**

 * @description:   shiro  配置文件
 * @author: wjy329
 * @param:
 * @return:
 * @create: 2019-01-12 21:21
 **/
@Configuration
public class ShiroConfig {
    @Autowired
    PermissionService permissionService;

    /**
    * @description  创建ShiroFilterFactoryBean
    * @author wjy329
    * @Date 2019/1/20
    */

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactory(@Qualifier("securityManager") SecurityManager manager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        bean.setSecurityManager(manager);

        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauth");

        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/index","authc");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/logout","anon");
        filterChainDefinitionMap.put("/unauth","anon");

        filterChainDefinitionMap.put("/register", "anon");
        // 不需要拦截的静态文件请求
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/kaptcha.jpg", "anon");
        filterChainDefinitionMap.put("/wp-content/**","anon");
        filterChainDefinitionMap.put("/upload/**","anon");
        // 404错误界面
        filterChainDefinitionMap.put("/404", "anon");
        filterChainDefinitionMap.put("/page/**","anon");

        filterChainDefinitionMap.putAll(permissionService.getUrlPerm());
        filterChainDefinitionMap.put("/**", "authc");

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        return bean;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher") CredentialMatcher matcher){
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCacheManager(new MemoryConstrainedCacheManager());
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher(){
        return new CredentialMatcher();
    }


    @Bean(name = "shiroDialect")

    public ShiroDialect shiroDialect(){

        return new ShiroDialect();

    }

}
