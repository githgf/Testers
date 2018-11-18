package cn.hgf.springdemo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: FanYing
 * @Date: 2018-07-12 9:22
 * @Desciption:     文件上传
 */
@Configuration
@EnableConfigurationProperties(MultipartProperties.class)
public class FileUploadConfig {

    //文件导出本地的路径
    public static String filePath;

    private final MultipartProperties multipartProperties;
    public FileUploadConfig(MultipartProperties multipartProperties){
        this.multipartProperties=multipartProperties;
    }
    /**
 * 注册解析器
 * @return
 */
    @Bean(name= DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(MultipartResolver.class)
    public StandardServletMultipartResolver multipartResolver(){
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        multipartResolver.setResolveLazily(multipartProperties.isResolveLazily());
        return multipartResolver;
    }
    /**
 * 上传配置
 * @return
 */
    @Bean
    @ConditionalOnMissingBean
    public MultipartConfigElement multipartConfigElement(){
        return this.multipartProperties.createMultipartConfig();
    }

    public static String getFilePath() {
        return filePath;
    }

    @Value("${common.filePath}")
    public void setFilePath(String filePath) {
        FileUploadConfig.filePath = filePath;
    }
}