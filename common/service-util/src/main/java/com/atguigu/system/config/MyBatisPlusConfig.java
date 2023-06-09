package com.atguigu.system.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisPlus的配置类:
 * 含拦截器
 */
@SpringBootConfiguration
@EnableTransactionManagement
@MapperScan(basePackages = "com.atguigu.system.mapper")
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor getMyBatisPlusInterceptor() {
        //创建MybatisPlusInterceptor对象,MybatisPlus自带的拦截器
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //设置分页拦插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }
}
