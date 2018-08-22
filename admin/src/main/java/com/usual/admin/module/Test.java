package com.usual.admin.module;

import com.usual.admin.common.util.RedisDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Test {

    @Resource
    private RedisDao redisDao;

    @RequestMapping("/test")
    public String test() {
        redisDao.set("ok", "123");
        System.out.printf(redisDao.get("ok").toString());
        return "test";
    }
}
