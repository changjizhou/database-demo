package cn.ktwo.databasedemo.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * email changji_z@163.com
 * auther: ktwo
 * date 2018/12/13 23:26
 */
@Order(1)
@Aspect
@Component
public class DataSourceAop {

    static Logger log = LoggerFactory.getLogger(DataSourceAop.class);

    @Pointcut("!@annotation(cn.ktwo.databasedemo.config.Master) " +
            "&& (execution(* cn.ktwo.databasedemo.service..*.select*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.query*(..))"+
            "|| execution(* cn.ktwo.databasedemo.service..*.get*(..)))")
    public void readPointcut() {

    }

    @Pointcut("@annotation(cn.ktwo.databasedemo.config.Master) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.insert*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.add*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.update*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.edit*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.delete*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.remove*(..))")
    public void writePointcut() {

    }


    /*final int readSize = Integer.parseInt("2");
    private AtomicInteger count = new AtomicInteger(0);*/

    @Before("readPointcut()")
    public void read(JoinPoint point) {

       /* //读库， 简单负载均衡
        int number = count.getAndAdd(1);
        int lookupKey = number % readSize;
        System.err.println("使用数据库read-"+(lookupKey+1));
        return DataSourceType.read.getType()+(lookupKey+1);*/


        MyRoutingDataSource.setDataSource(MyRoutingDataSource.READ);
        log.info("dataSource切换到：Read");

    }

    @Before("writePointcut()")
    public void write() {
        MyRoutingDataSource.setDataSource(MyRoutingDataSource.WRITE);
        log.info("dataSource切换到：write");
    }


    /**
     * 上面那种写法也可以用下面这种实现
     */

    /*@Before("!@annotation(cn.ktwo.databasedemo.config.Master) " +
            "&& (execution(* cn.ktwo.databasedemo.service..*.select*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.get*(..)))")
    public void setReadDataSourceType() {
        DBContextHolder.read();
        log.info("dataSource切换到：Read");
    }

    @Before("@annotation(cn.ktwo.databasedemo.config.Master) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.insert*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.add*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.update*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.edit*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.delete*(..)) " +
            "|| execution(* cn.ktwo.databasedemo.service..*.remove*(..))")
    public void setWriteDataSourceType() {
        DBContextHolder.write();
        log.info("dataSource切换到：write");
    }*/


    /**
     * 另一种写法：if...else...  判断哪些需要读从数据库，其余的走主数据库
     */
//    @Before("execution(* cn.ktwo.databasedemo.service.impl.*.*(..))")
//    public void before(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
//            DBContextHolder.slave();
//        }else {
//            DBContextHolder.master();
//        }
//    }
}
