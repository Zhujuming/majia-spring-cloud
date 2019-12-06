package com.zhujuming.vip.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhujuming.vip.constants.ResponseCode;
import com.zhujuming.vip.mq.MessageSender;
import com.zhujuming.vip.service.MemberService;
import com.zhujuming.vip.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MemberController implements MemberService {

    @Autowired
    private MessageSender messageSender;

    @Value("${activemq.queue}")
    private String queue;

    @Override
    public ResponseVO register(String email, String password) {
        log.info("email={},password={}", email, password);
        JSONObject json = new JSONObject();
        json.put("type", "email");
        json.put("to", email);
        messageSender.send(queue, json.toJSONString());
        return new ResponseVO(ResponseCode.OK, "注册成功");
    }

//    @RequestMapping("/getMember")
//    public String getMember() {
//        try {
//            Thread.sleep(3000);
//            return "this is getMember（生产者）";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "RERROR";
//        }
//    }
}
