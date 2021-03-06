package com.zhujuming.vip.service;


import com.zhujuming.vip.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/member")
public interface MemberService {

    @RequestMapping("/register")
    public ResponseVO register(@RequestParam("email") String email, @RequestParam("password") String password);

}
