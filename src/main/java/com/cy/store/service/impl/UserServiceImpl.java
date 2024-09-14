package com.cy.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username=user.getUsername();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> result= userMapper.selectList(queryWrapper);
        if(result.size()!=0){
            throw new UsernameDuplicateException("用户名已存在");
        }
        Date now=new Date();
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        user.setCreatedTime(now);
        user.setModifiedTime(now);
        String oldPWD=user.getPassword();
        String salt=UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String newPWD=getMd5Password(oldPWD,salt);
        user.setPassword(newPWD);
        int rows=userMapper.insert(user);
        if(rows!=1){
            throw new InsertException("添加用户出现未知错误，请联系管理员。");
        }
    }

    @Override
    public User login(String username, String password) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> result = userMapper.selectList(queryWrapper);
        // 判断查询结果是否为null
        if (result.size()==0) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在的错误");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.get(0).getIsDelete() == 1) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在的错误");
        }

        // 从查询结果中获取盐值
        String salt = result.get(0).getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(password, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!result.get(0).getPassword().equals(md5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("密码验证失败的错误");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.get(0).getUid());
        user.setUsername(result.get(0).getUsername());
        user.setAvatar(result.get(0).getAvatar());
        // 返回新的user对象
        return user;
    }

    @Override
    public void changePWD(long uid, String username, String oldPassword, String newPassword) {
        User result= userMapper.selectById(uid);
        if(result==null)throw new UserNotFoundException("用户数据不存在的错误");
        if(result.getIsDelete()==1)throw new UserNotFoundException("用户已被注销");
        oldPassword=getMd5Password(oldPassword,result.getSalt());
        if(!oldPassword.equals(result.getPassword()))throw new PasswordNotMatchException("密码验证失败的错误");
        User user=new User();
        Date now=new Date();
        newPassword=getMd5Password(newPassword,result.getSalt());
        user.setUid(uid);
        user.setPassword(newPassword);
        user.setModifiedUser(username);
        user.setModifiedTime(now);
        int row=userMapper.updateById(user);
        if(row!=1)throw new UpdatePasswordException("更改密码出现未知错误，请联系管理员");
    }

    @Override
    public void changeInfo(long uid,String username,User user) {
        User result=userMapper.selectById(uid);
        if(result==null)throw new UserNotFoundException("用户不存在");
        if(result.getIsDelete()==1)throw new UserNotFoundException("用户已被注销");
        Date now=new Date();
        user.setUid(uid);
        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(now);
        int row=userMapper.updateById(user);
        if(row!=1)throw new UpdateInfoException("修改个人资料出现未知错误");
    }

    @Override
    public void changeAvatar(long uid, String username, String avatar) {
        User result=userMapper.selectById(uid);
        if(result==null)throw new UserNotFoundException("用户不存在");
        if(result.getIsDelete()==1)throw new UserNotFoundException("用户已被注销");
        Date now=new Date();
        User user=new User();
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(now);
        user.setAvatar(avatar);
        int row=userMapper.updateById(user);
        if(row!=1)throw new FileUploadAvatarException("上传头像出现未知错误");
    }

    @Override
    public User getInfo(long uid) {
        User result= userMapper.selectById(uid);
        if(result==null)throw new UserNotFoundException("用户不存在");
        if(result.getIsDelete()==1)throw new UserNotFoundException("用户已被注销");
        //这里要重新创建一个新的用户信息
        User user=new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
