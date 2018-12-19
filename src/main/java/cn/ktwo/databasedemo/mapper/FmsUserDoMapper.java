package cn.ktwo.databasedemo.mapper;

import cn.ktwo.databasedemo.entity.FmsUserDo;
import cn.ktwo.databasedemo.entity.FmsUserDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FmsUserDoMapper {
    long countByExample(FmsUserDoExample example);

    int deleteByExample(FmsUserDoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FmsUserDo record);

    int insertSelective(FmsUserDo record);

    List<FmsUserDo> selectByExample(FmsUserDoExample example);

    FmsUserDo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FmsUserDo record, @Param("example") FmsUserDoExample example);

    int updateByExample(@Param("record") FmsUserDo record, @Param("example") FmsUserDoExample example);

    int updateByPrimaryKeySelective(FmsUserDo record);

    int updateByPrimaryKey(FmsUserDo record);
}