package cn.hgf.springdemo.common.config;

import cn.hgf.springdemo.common.entities.CustomerArgResolver;
import cn.hgf.springdemo.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: FanYing
 * @Date: 2018-05-01 13:44
 * @Desciption: spring mvc 的配置类
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    /**
     *  设置默认的首页
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login.html")
                .excludePathPatterns("/login/verify")
                .excludePathPatterns("/");

        super.addInterceptors(registry);
    }

    @Bean
    public CommonsMultipartResolver commonsMultipartResolver(){

        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxInMemorySize(5000000);
        commonsMultipartResolver.setMaxUploadSize(6000000);

        return  commonsMultipartResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new CustomerArgResolver());
    }
}
