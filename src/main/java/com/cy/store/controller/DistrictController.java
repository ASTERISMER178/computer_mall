package com.cy.store.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.store.entity.District;
import com.cy.store.service.DistrictService;
import com.cy.store.util.JsonResult;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {
    @Autowired
    private DistrictService districtService;
    @GetMapping({"","/"})
    public JsonResult<Void>getByParent(String parent){
        System.out.println("AA");
        System.out.println(parent);
        JsonResult<Void> jsonResult=new JsonResult<>();
        List<District> data=districtService.getByParent(parent);
        System.out.println(data.get(0));
        System.out.println("BB");
        jsonResult.setCode(OK);
        jsonResult.setMessage("SUCCESS");
        jsonResult.setData(data);
        return jsonResult;
    }
}
