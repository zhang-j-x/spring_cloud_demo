package com.jx.order.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangjx
 * @Date: 2021-03-11
 **/
@Configuration
public class DataSourceProxyConfig {
    @Bean("originMaster")
    @ConfigurationProperties(prefix = "spring.datasource.order-master")
    public DataSource dataSourceMaster() {
        return new DruidDataSource();
    }

    @Bean("originSlave")
    @ConfigurationProperties(prefix = "spring.datasource.order-slave")
    public DataSource dataSourceStorage() {
        return new DruidDataSource();
    }


    @Bean(name = "master")
    public DataSourceProxy masterDataSourceProxy(@Qualifier("originMaster") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "slave")
    public DataSourceProxy storageDataSourceProxy(@Qualifier("originSlave") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }



    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("master") DataSource masterDataSource,
                                        @Qualifier("slave") DataSource slaveDataSource) {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceKey.MASTER.name(), masterDataSource);
        dataSourceMap.put(DataSourceKey.SLAVE.name(), slaveDataSource);

        dynamicRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        DynamicDataSourceContextHolder.getDataSourceKeys().addAll(dataSourceMap.keySet());
        return dynamicRoutingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) {
        // 这里用 MybatisSqlSessionFactoryBean 代替了 SqlSessionFactoryBean，否则 MyBatisPlus 不会生效
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        return mybatisSqlSessionFactoryBean;
    }
}
