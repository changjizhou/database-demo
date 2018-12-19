package cn.ktwo.databasedemo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * email changji_z@163.com
 * auther: ktwo
 * date 2018/12/13 22:46
 */
@Configuration
public class DataSourceConfig {

    private static Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;
    @Value("${druid.init.prefix}")
    private String prefix;
    @Value("${druid.init.allow}")
    private String allow;
    @Value("${druid.init.deny}")
    private String deny;
    @Value("${druid.init.loginUsername}")
    private String loginUsername;
    @Value("${druid.init.loginPassword}")
    private String loginPassword;
    @Value("${druid.init.resetEnable}")
    private String resetEnable;

    @Bean
    public ServletRegistrationBean druidServlet() {
        log.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), prefix);
        // IP白名单
        servletRegistrationBean.addInitParameter("allow", allow);
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", deny);
        // 控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", loginUsername);
        servletRegistrationBean.addInitParameter("loginPassword", loginPassword);
        // 是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", resetEnable);
        return servletRegistrationBean;

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico," + prefix);
        return filterRegistrationBean;
    }

    @Bean(name = "writeDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource writeDataSource() {
        log.info("-------------------- writeDataSource init ---------------------");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource readDataSource() {
        log.info("-------------------- readDataSourceOne init ---------------------");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public MyRoutingDataSource myRoutingDataSource() {
        DataSource writeDataSource = writeDataSource();
        DataSource readDataSource = readDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(MyRoutingDataSource.WRITE, writeDataSource);
        targetDataSources.put(MyRoutingDataSource.READ, readDataSource);
        MyRoutingDataSource dataSource = new MyRoutingDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(writeDataSource);
        return dataSource;
    }


    //XxxMapper.xml文件所在路径
    @Value("${spring.datasource.mapperLocations}")
    private String mapperLocations;

    //  加载全局的配置文件
    @Value("${spring.datasource.configLocation}")
    private String configLocation;



    @Bean
    public SqlSessionFactory sqlSessionFactorys() throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(myRoutingDataSource());
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }




    /*@Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactorys() throws Exception {
        log.info("--------------------  sqlSessionFactory init ---------------------");
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

            sessionFactoryBean.setDataSource(myRoutingDataSource());

            // 读取配置
            sessionFactoryBean.setTypeAliasesPackage("cn.ktwo.databasedemo.entity");

            //设置mapper.xml文件所在位置
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
            sessionFactoryBean.setMapperLocations(resources);
            //设置mybatis-config.xml配置文件位置
            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            log.error("mybatis resolver mapper*xml is error",e);
            return null;
        } catch (Exception e) {
            log.error("mybatis sqlSessionFactoryBean create error",e);
            return null;
        }
    }*/



}
