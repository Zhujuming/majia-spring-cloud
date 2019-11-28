package com.zhujuming.vip.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController {

    @RequestMapping("/getMember")
    public String getMember() {
        try {
            Thread.sleep(3000);
            return "this is getMember（生产者）";
        } catch (Exception e) {
            e.printStackTrace();
            return "RERROR";
        }
    }
}
