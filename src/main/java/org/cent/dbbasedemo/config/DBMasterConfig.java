package org.cent.dbbasedemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 主数据源配置
 * @author Vincent
 * @version 1.0 2019/11/23
 */
@Configuration
// 多套数据源，要指定mapper接口使用数据源
@MapperScan(basePackages = "org.cent.dbbasedemo.mapper.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class DBMasterConfig {

    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
        return new DruidDataSource();
    }

    @Primary
    @Bean(name = "masterTransactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/master/*.xml"));
        return factoryBean.getObject();
    }

    @Primary
    @Bean(name = "masterSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
