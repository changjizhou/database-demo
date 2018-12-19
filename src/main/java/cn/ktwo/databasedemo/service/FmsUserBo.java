package cn.ktwo.databasedemo.service;

import cn.ktwo.databasedemo.entity.FmsUserDo;

import java.util.List;

public interface FmsUserBo {

    FmsUserDo insert(FmsUserDo userDo);

    List<FmsUserDo> query();

    int delete(Long id);

    FmsUserDo update(FmsUserDo userDo);

    List<FmsUserDo> getUserByName(String name);
}
