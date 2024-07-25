package com.itheima.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * CommonImportSelector
 * 
 * @author dingtao
 * @date 2024/7/25 10:30
 */
public class CommonImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.itheima.config.CommonConfig"};
    }
}
