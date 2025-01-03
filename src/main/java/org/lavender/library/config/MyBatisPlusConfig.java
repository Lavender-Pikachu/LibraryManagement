package org.lavender.library.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ExceptionSorter;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Properties;

@Configuration //表明该类是一个配置类
@MapperScan("org.lavender.library.mapper") //扫描配置，mapper下的接口
public class MyBatisPlusConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String jdbcDriver;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    // 最新版
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean(name="dataSource")
    public DruidDataSource createDataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);

        // 关闭连接后不自动commit
        dataSource.setDefaultAutoCommit(false);
        // 设置连接异常处理
        dataSource.setBreakAfterAcquireFailure(true);
        // 将SQLException抛出重要配置
        dataSource.setFailFast(true);
        dataSource.setConnectionErrorRetryAttempts(0);
        // 配置自定义的异常处理器
        dataSource.setExceptionSorter(new CustomExceptionSorter());

        // 关闭Druid连接池内部的异常处理
//    dataSource.setFilters("stat");
        return dataSource;
    }
}


class CustomExceptionSorter implements ExceptionSorter {

    @Override
    public boolean isExceptionFatal(SQLException e) {
        // 将所有异常视为致命异常，即抛出到上层
        // 打印异常堆栈信息
        e.printStackTrace();
        return true;
    }

    @Override
    public void configFromProperties(Properties properties) {
        // 配置信息可以为空
    }
}