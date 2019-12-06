package com.zhujuming.vip.adapter.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhujuming.vip.adapter.MsgAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

@Component
public class EmailAdapter implements MsgAdapter {

    @Resource
    private JavaMailSender mailSender;

    @Override
    public boolean excute(String json) throws JmsException {
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("893371542@qq.com");
            helper.setTo("1828731520@qq.com");
            helper.setSubject("SpringBoot 集成QQ邮箱");
            helper.setText("seven-test-send-eamil");
            this.mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //    @Resource
//    private JavaMailSender mailSender;
//
//    @Value("${spring.mail.username}")
//    private String systemmail;
//
//    @Override
//    public boolean excute(String json) {
//        try {
//            JSONObject object = JSONObject.parseObject(json);
//            String to = (String)object.get("to");
//            String msg = "你好啊," + to;
//            MimeMessage mimeMessage = mailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"utf-8");
//            mimeMessageHelper.setFrom(systemmail);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setSubject("注册通知！");
//            mimeMessageHelper.setText(msg,true);
//            mailSender.send(mimeMessage);
//            return  true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
