package org.cent.dbbasedemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 从数据源配置
 * @author Vincent
 * @version 1.0 2019/11/24
 */
@Configuration
// 多套数据源，要指定mapper接口使用数据源
@MapperScan(basePackages = "org.cent.dbbasedemo.mapper.slave", sqlSessionTemplateRef = "slaveSqlSessionTemplate")
public class DBSlaveConfig {

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
        return new DruidDataSource();
    }

    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("slaveDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("slaveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/slave/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "slaveSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
