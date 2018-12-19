package cn.ktwo.databasedemo.config;

/**
 * 有一般情况就有特殊情况，特殊情况是某些情况下我们需要强制读主库，针对这种情况，我们定义一个主键，用该注解标注的就读主库
 * email changji_z@163.com
 * auther: ktwo
 * date 2018/12/13 23:28
 */
public @interface Master {
}
