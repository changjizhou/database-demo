package cn.ktwo.databasedemo.config;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * email changji_z@163.com
 * auther: ktwo
 * date 2018/12/15 23:42
 */
public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {
    /**
     * 只读事务到读库，读写事务到写库
     * @param transaction
     * @param definition
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {

        //设置数据源
        boolean readOnly = definition.isReadOnly();
        if(readOnly) {
            MyRoutingDataSource.setDataSource(MyRoutingDataSource.READ);
        } else {
            MyRoutingDataSource.setDataSource(MyRoutingDataSource.WRITE);
        }
        super.doBegin(transaction, definition);
    }

    /**
     * 清理本地线程的数据源
     * @param transaction
     */
    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        MyRoutingDataSource.clearDataSource();
    }
}
