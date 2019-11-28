package com.zhujuming.vip.consumer;

import com.zhujuming.vip.hystrix.MemberHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "spring-cloud-member",fallbackFactory = MemberHystrix.class)
public interface MemberConsumer {

    @PostMapping("/getMember")
    public String getMember();
}
