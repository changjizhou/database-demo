package cn.ktwo.databasedemo;

import cn.ktwo.databasedemo.config.MyRoutingDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan({"cn.ktwo.*.mapper"})
@ComponentScan(basePackages = "cn.ktwo",includeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ,pattern = {"cn.ktwo..*BoImpl"})})
public class DatabaseDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseDemoApplication.class, args);
    }

}

