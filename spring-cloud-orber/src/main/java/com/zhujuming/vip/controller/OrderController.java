package com.zhujuming.vip.controller;

import com.zhujuming.vip.consumer.MemberConsumer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("xxx控制层")
@Slf4j
@RestController
@EnableEurekaClient
public class OrderController {

    @Autowired
    private MemberConsumer memberConsumer;

    /**
     * order 使用rpc 远程调用技术调用会员服务
     * String memberUrl = "http://spring-cloud-member/getMember";
     * String result = restTemplate.getForObject(memberUrl, String.class);
     */
    @ApiOperation(value = "Order-获取订单接口", httpMethod = "POST", notes = "Order-获取订单接口")
    @RequestMapping(value = "/v1/order/getOrder", method = RequestMethod.POST)
    public String getOrder() {
        log.info("...info...");
        String member = memberConsumer.getMember();
        return "订单服务调（消费者）用会员服务（生产者）:" + member;
    }
}
