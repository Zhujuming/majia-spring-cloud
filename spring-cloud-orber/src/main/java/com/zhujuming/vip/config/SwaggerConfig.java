package com.zhujuming.vip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.show}")
    private boolean enableSwagger;

    @Bean
    public Docket api() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("Content-token").description("token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2).groupName("apply").enable(enableSwagger)
                .genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false)
                .forCodeGeneration(false).pathMapping("/").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.zhujuming.vip.controller")).build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("SpringCloud框架搭建学习").description("平台接口文档").termsOfServiceUrl("")
                .contact(new Contact("Seven", "", "")).version("1.0").build();
    }

    //访问地址:http://127.0.0.1:端口/swagger-ui.html
}
