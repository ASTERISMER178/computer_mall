package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.BaseEntity;
import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.util.JsonResult;
import com.sun.applet2.preloader.event.InitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.cy.store.controller.BaseController.OK;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        JsonResult<Void> jsonResult=new JsonResult<>();
        userService.reg(user);
        jsonResult.setCode(OK);
        return jsonResult;
    }
    @RequestMapping("login")
    public JsonResult<Void> login(String username, String password, HttpSession session){
        JsonResult<Void> jsonResult=new JsonResult<>();
        User data=userService.login(username,password);
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        jsonResult.setCode(OK);
        jsonResult.setData(data);
        return jsonResult;
    }
    @RequestMapping("changePWD")
    public JsonResult<Void> changePWD(String oldPassword,String newPassword,HttpSession session){
        JsonResult<Void> jsonResult=new JsonResult<>();
        userService.changePWD(getUidFromSession(session),getUsernameFromSession(session),oldPassword,newPassword);
        jsonResult.setCode(OK);
        return jsonResult;
    }
    @RequestMapping("get_by_uid")
    public JsonResult<Void> getInfo(HttpSession session){
        JsonResult<Void> jsonResult=new JsonResult<>();
        Integer uid=getUidFromSession(session);
        User data= userService.getInfo(uid);
        jsonResult.setCode(OK);
        jsonResult.setData(data);
        return jsonResult;
    }
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        JsonResult<Void> jsonResult=new JsonResult<>();
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
//        System.out.println(user.getUsername());
//        System.out.println(user.getPhone());
//        System.out.println(user.getEmail());
        userService.changeInfo(uid,username,user);
        jsonResult.setCode(OK);
        return jsonResult;
    }
    public static final Integer AVATAR_MAX_SIZE=10*1024*1024;
    public static final List<String> AVATAR_TYPE=new ArrayList<String>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/bmp");
    }
    @RequestMapping("change_avatar")
    public JsonResult<Void>changeAvatar(MultipartFile file, HttpSession session){
//        System.out.println("AA");
        JsonResult<Void> jsonResult=new JsonResult<>();

        if(file.isEmpty()){throw new FileEmptyException("图片不能为空");}

        if(file.getSize()>AVATAR_MAX_SIZE)throw new FileSizeException("图片大小超过限制");
//        System.out.println("DD");
        if(!AVATAR_TYPE.contains(file.getContentType()))throw new FileTypeException("图片格式错误");
//        System.out.println("BB");
        String parent=session.getServletContext().getRealPath("upload");
        File dir=new File(parent);
        boolean flag;
        if(!dir.exists())flag=dir.mkdirs();
        String originalFilename=file.getOriginalFilename();
        assert originalFilename != null;
        Integer index= originalFilename.lastIndexOf(".");
//        System.out.println(index);
        String suffix=originalFilename.substring(index);
        String newFilename= UUID.randomUUID().toString()+suffix;
//        System.out.println(newFilename);
        File des=new File(dir,newFilename);
        try{
            file.transferTo(des);
        }catch (IllegalStateException e){
            throw new FileStateException("文件上传状态异常");
        }catch (IOException e){
            throw new FileUploadIOException("上传文件时读写异常");
        }
        String avatar="/upload/"+newFilename;
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changeAvatar(uid,username,avatar);
        jsonResult.setCode(OK);
        jsonResult.setData(avatar);
        return jsonResult;
    }


}
