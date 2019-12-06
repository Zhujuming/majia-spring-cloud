package com.zhujuming.vip.receive;

import com.alibaba.fastjson.JSONObject;
import com.zhujuming.vip.adapter.MsgAdapter;
import com.zhujuming.vip.adapter.impl.EmailAdapter;
import com.zhujuming.vip.adapter.impl.WeiXinAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MsgHandler {

    @Autowired
    private EmailAdapter emailAdapter;
    @Autowired
    private WeiXinAdapter weiXinAdapter;

    private MsgAdapter msgAdapter;

    //使用JmsListener配置消费者监听的队列,其中text是接收到的信息
    @JmsListener(destination = "${activemq.queue}")
    public void receiveQueue(String json) throws Exception {
        System.out.println("####消息服务收到的报文为：" + json);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String type = (String) jsonObject.get("type");
        switch (type) {
            case "email":
                msgAdapter = emailAdapter;
                break;
            case "sms":
                msgAdapter = weiXinAdapter;
                break;
            default:
                break;
        }
        msgAdapter.excute(json);
    }
}
