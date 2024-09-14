package com.cy.store.service;

import com.cy.store.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

public interface UserService {
    public void reg(User user);
    public User login(String username, String password);
    //业务层更改密码
    public void changePWD(long uid,String username,String oldPassword,String newPassword);

    //获取个人信息用来展示
    public User getInfo(long uid);
    //业务层更改个人资料
    void changeInfo(long uid,String username,User user);
    //业务层更换头像
    void changeAvatar(long uid,String username,String avatar);
    //业务层添加收货地址

}
