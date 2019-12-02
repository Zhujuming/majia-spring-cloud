package com.zhujuming.vip.aop;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhujuming.vip.utils.HttpUtils;
import com.zhujuming.vip.utils.Serializer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Aspect: 将一个java类定义为切面类
 * @Component: 把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    Map params = Maps.newLinkedHashMap();

    /**
     * 以controller包下定义的所有请求为切入点
     * 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
     */
    @Pointcut("execution(public * com.zhujuming.vip.webapp.controller..*.*(..)) ")
    public void webLog() {
    }

    /**
     * 使用@Before在切入点开始处切入内容
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        params.put("【请求的路径】", request.getRequestURL()); // 获取请求的url
        params.put("【请求的方式】", request.getMethod()); // 获取请求的方式
        params.put("【请求的地址】", HttpUtils.getIpAddr(request)); // 获取请求的ip地址
        params.put("【请求全类名】", joinPoint.getSignature().getDeclaringTypeName()); // 获取类名
        params.put("【请求方法名】", joinPoint.getSignature().getName()); // 获取类方法
        params.put("【请求的参数】", Serializer.serialize(joinPoint.getArgs())); // 请求参数
    }

    /**
     * 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     *
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter() throws Throwable {
        //输出格式化后的json字符串
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        /**
         * TODO 转换报错，注释下面代码
         */
        log.info(gson.toJson(params));
        //清空每次内容
        params.clear();
        // 每个请求之间空一行
        log.info("");
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        params.put("【请求的返回】", result); // 响应回包参数
        params.put("【请求的耗时】", (System.currentTimeMillis() - startTime.get()) + "ms");
        return result;
    }
}

