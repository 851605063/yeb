package com.qfggk.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 测试
 * @author: WJQ
 * @date 2021-06-14 15:18
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}

