package cn.ktwo.databasedemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * email changji_z@163.com
 * auther: ktwo
 * date 2018/12/13 23:35
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {

    private static Logger log = LoggerFactory.getLogger(MyRoutingDataSource.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static final String WRITE = "writeDataSource";  // 写库
    public static final String READ = "readDataSource";  // 读库

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("******************" + contextHolder.get());
        return getDataSource();
    }

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }


/*
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    protected Object determineCurrentLookupKey() {

        log.info("***MyRoutingDataSource");

        String typeKey = DBContextHolder.getJdbcType();
        if (typeKey.equals(DataSourceType.WRITE.getType())) {
            return DataSourceType.WRITE.getType();
        }
        return DataSourceType.READ.getType();

        // 读 简单负载均衡
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        Integer i = lookupKey;
        return i;
    }

    */


}
