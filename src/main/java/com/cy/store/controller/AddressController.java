package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.AddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {
    @Autowired
    private AddressService addressService;
    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        JsonResult<Void> jsonResult=new JsonResult<>();
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        jsonResult.setCode(OK);
        jsonResult.setMessage("SUCCESS");
        return jsonResult;
    }
    @GetMapping({"","/"})
    public JsonResult<Void> getByUid(HttpSession session){
        Integer uid=getUidFromSession(session);
        JsonResult<Void> jsonResult=new JsonResult<>();
        List<Address> data=addressService.getByUid(uid);
        jsonResult.setCode(OK);
        jsonResult.setMessage("SUCCESS");
        jsonResult.setData(data);
        return jsonResult;
    }
}
