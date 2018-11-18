package cn.hgf.springdemo.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: FanYing
 * @Date: 2018-05-20 10:02
 * @Desciption:
 */
@MapperScan(value = "cn.hgf.springdemo.mapper")
@Configuration
public class MyBatisConfig {

    /**
     * 配置mybatis
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
