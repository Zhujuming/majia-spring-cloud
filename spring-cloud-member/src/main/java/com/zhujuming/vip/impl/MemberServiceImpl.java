package com.zhujuming.vip.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhujuming.vip.constants.ResponseCode;
import com.zhujuming.vip.mq.MessageSender;
import com.zhujuming.vip.service.MemberService;
import com.zhujuming.vip.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MessageSender messageSender;

    @Override
    public ResponseVO register(String email, String password) {
        JSONObject json = new JSONObject();
        json.put("type","email");
        json.put("to",email);
        messageSender.send("seven",json.toJSONString());

        System.out.println("email:"+email);
        System.out.println("password:"+password);
        return new ResponseVO(ResponseCode.OK,"测试成功");
    }
}
