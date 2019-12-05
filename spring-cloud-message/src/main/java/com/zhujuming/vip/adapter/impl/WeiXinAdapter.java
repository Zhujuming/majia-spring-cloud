package com.zhujuming.vip.adapter.impl;

import com.zhujuming.vip.adapter.MsgAdapter;
import org.springframework.stereotype.Component;

@Component
public class WeiXinAdapter implements MsgAdapter {

    @Override
    public boolean excute(String json) {
        System.out.println("处理微信相关的业务");
        return true;
    }
}
