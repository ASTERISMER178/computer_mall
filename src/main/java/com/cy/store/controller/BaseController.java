package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;
import java.rmi.ServerException;

public class BaseController {
    public static final int OK=200;
    @ExceptionHandler({ServerException.class,RuntimeException.class})//可以自动捕获ServerException类型的异常并且将异常对象送到方法的参数列表中。
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result=new JsonResult<>();
        if(e instanceof UsernameDuplicateException){
            result.setCode(4000);
            result.setMessage("用户名已存在");
        }else if(e instanceof InsertException){
            result.setCode(4001);
            result.setMessage("注册时产生未知错误");
        }else if(e instanceof UserNotFoundException){
            result.setCode(5000);
            result.setMessage("用户名不存在或者未注册");
        }else if(e instanceof PasswordNotMatchException){
            result.setCode(5001);
            result.setMessage("密码错误");
        }else if(e instanceof UpdatePasswordException){
            result.setCode(5002);
            result.setMessage("更改密码时出现未知错误");
        }else if(e instanceof UpdateInfoException){
            result.setCode(5003);
            result.setMessage("修改个人信息时出现未知错误");
        }else if(e instanceof FileUploadAvatarException){
            result.setCode(6001);
            result.setMessage("上传头像出现未知问题");
        }else if(e instanceof FileEmptyException){
            result.setCode(6002);
            result.setMessage("上传头像不能为空");
        }else if(e instanceof FileStateException){
            result.setCode(6003);
            result.setMessage("上传状态错误");
        }else if(e instanceof FileTypeException){
            result.setCode(6004);
            result.setMessage("图片格式错误");
        }else if(e instanceof FileSizeException){
            result.setCode(6005);
            result.setMessage("图片大小超过限制");
        }else if(e instanceof FileUploadIOException){
            result.setCode(6006);
            result.setMessage("图片上传失败");
        }else if(e instanceof AddNewAddressException){
            result.setCode(7001);
            result.setMessage("添加地址出现未知错误");
        }else if(e instanceof AddressCountLimitException){
            result.setCode(7002);
            result.setMessage("地址数量超限");
        }
        return result;
    }

    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
