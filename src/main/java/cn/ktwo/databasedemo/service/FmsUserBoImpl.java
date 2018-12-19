package cn.ktwo.databasedemo.service;

import cn.ktwo.databasedemo.config.Master;
import cn.ktwo.databasedemo.entity.FmsUserDo;
import cn.ktwo.databasedemo.entity.FmsUserDoExample;
import cn.ktwo.databasedemo.mapper.FmsUserDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.List;

public class FmsUserBoImpl implements FmsUserBo {


    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    FmsUserDoMapper userDoMapper ;

    @Override
    public FmsUserDo insert(FmsUserDo userDo) {
        userDoMapper.insert(userDo);
        return userDo;
    }

    @Override
    public List<FmsUserDo> query() {
        FmsUserDoExample example = new FmsUserDoExample();
        List<FmsUserDo> list = userDoMapper.selectByExample(example);
        return list;
    }

    @Override
    public int delete(Long id) {
        return userDoMapper.deleteByPrimaryKey(id.intValue());
    }

    @Override
    public FmsUserDo update(FmsUserDo userDo) {
        userDoMapper.updateByPrimaryKey(userDo);
        return userDo;
    }

    @Override
    @Master
    public List<FmsUserDo> getUserByName(String name) {
        FmsUserDoExample userDoExample = new FmsUserDoExample();
        userDoExample.createCriteria().andNameEqualTo(name);
        return userDoMapper.selectByExample(userDoExample);
    }
}
