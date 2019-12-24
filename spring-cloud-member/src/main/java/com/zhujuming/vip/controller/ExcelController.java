package com.zhujuming.vip.controller;

import com.zhujuming.vip.data.ExeclTemplateListData;
import com.zhujuming.vip.data.ExeclTemplateData;
import com.zhujuming.vip.utils.ExcelUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO
 */
@Controller
@RequestMapping("/api/excel")
public class ExcelController {

    @GetMapping("/testDownloadExecl")
    @ApiOperation(value = "下载订单详细信息")
    public void testDownloadExecl(HttpServletResponse response) {

        List<ExeclTemplateListData> execlTemplateListDataList = new ArrayList<>();
        ExeclTemplateListData execlTemplateListData = new ExeclTemplateListData();
        execlTemplateListData.setId(1);
        execlTemplateListData.setName("Seven Zhu");
        execlTemplateListData.setAddress("湖北省恩施土家族苗族自治州");
        execlTemplateListData.setBirthday("1993-12-26");
        execlTemplateListData.setEmail("893371542@qq.com");
        execlTemplateListData.setPhoneNo("13902434787");
        execlTemplateListData.setHobby("养宠物");
        execlTemplateListDataList.add(execlTemplateListData);

        List<ExeclTemplateData> execlTemplateDataList = new ArrayList<>();
        ExeclTemplateData data = new ExeclTemplateData();
        data.setHello("欢迎来来到湖北");
        data.setName("大湖北");
        data.setPhone("0718-88888888");
        data.setList(execlTemplateListDataList);
        execlTemplateDataList.add(data);
        ExcelUtils.initExport(ExeclTemplateData.class, response).doExcel(execlTemplateDataList, "测试POI通过注解方式下载Execl");
    }
}
