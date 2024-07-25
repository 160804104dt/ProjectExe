package com.itheima.springbootbeanregister;

import ch.qos.logback.core.testUtil.CoreTestConstants;
import com.itheima.config.CommonConfig;
import com.itheima.config.CommonImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(CommonConfig.class)
@Import(CommonImportSelector.class)
public class SpringbootBeanRegisterApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootBeanRegisterApplication.class, args);
        System.out.println(context.getBean("fff"));
    }
}
