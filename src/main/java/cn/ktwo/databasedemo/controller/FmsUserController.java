package cn.ktwo.databasedemo.controller;

import cn.ktwo.databasedemo.entity.FmsUserDo;
import cn.ktwo.databasedemo.service.FmsUserBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/fmsUser")
public class FmsUserController {

    @Autowired
    private FmsUserBo fmsUserBoImpl;

    @RequestMapping("/query")
    public List<FmsUserDo> query() {
        return fmsUserBoImpl.query();
    }

    @RequestMapping("/insert")
    public String insert() {
        FmsUserDo userDo = new FmsUserDo();
        userDo.setName("test");
        userDo.setPasswd("test");
        userDo.setBeApply("test");
        userDo.setCatalog("/");
        fmsUserBoImpl.insert(userDo);
        return "SUCCESS";
    }

    @RequestMapping("/get")
    public List<FmsUserDo> get() {
        String name = "test";
        return fmsUserBoImpl.getUserByName(name);
    }
}
