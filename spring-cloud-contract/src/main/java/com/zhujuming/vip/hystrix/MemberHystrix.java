package com.zhujuming.vip.hystrix;

import com.zhujuming.vip.consumer.MemberConsumer;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberHystrix implements FallbackFactory<MemberConsumer> {

    @Override
    public MemberConsumer create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            log.error(msg);
        }
        String rtn ="服务暂时不可用!";
        return new MemberConsumer(){
            @Override
            public String getMember() {
                return rtn;
            }
        };
    }
}
