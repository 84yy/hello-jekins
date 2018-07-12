package com.hzm.hellojekins.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <P>
 * description:
 * </p>
 *
 * @author 黄增猛
 * @since 2018/7/12 9:50
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "Hello Jekins";
    }

}
