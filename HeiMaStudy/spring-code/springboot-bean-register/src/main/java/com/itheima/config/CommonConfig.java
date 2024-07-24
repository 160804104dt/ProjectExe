package com.itheima.config;

import ch.qos.logback.core.testUtil.CoreTestConstants;
import ch.qos.logback.core.testUtil.FileTestUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CommonConfig
 *
 * @author dingtao
 * @date 2024/7/24 17:18
 */
@Configuration
public class CommonConfig {

    @Bean
    public FileTestUtil fileTestUtil() {
        return new FileTestUtil();
    }

    /**
     * 如果方法内部需要使用ioc容器中已经存在的bean对象，只需要在方法上声明即可，spring会自动注入
     * @param fileTestUtil
     * @return
     */
    @Bean("fff")
    public CoreTestConstants coreTestConstants(FileTestUtil fileTestUtil) {
        System.out.println(fileTestUtil);
        return new CoreTestConstants();
    }
}
