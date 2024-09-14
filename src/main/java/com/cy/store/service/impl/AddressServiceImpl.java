package com.cy.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.AddressService;
import com.cy.store.service.DistrictService;
import com.cy.store.service.ex.AddNewAddressException;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DistrictService districtService;
    @Value("${ADDRESS_MAX_COUNT}")
    private int maxCount;
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        User result=userMapper.selectById(uid);
        if(result==null||result.getIsDelete()==1)throw new UserNotFoundException("用户不存在");
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        long count=addressMapper.selectCount(queryWrapper);
        if(count>maxCount)throw new AddressCountLimitException("收货地址数量超限");
        int isDefault=count==0?1:0;
        address.setIsDefault(isDefault);
        address.setUid(uid);
        address.setModifiedUser(username);
        address.setCreatedUser(username);
        Date now=new Date();
        address.setModifiedTime(now);
        address.setCreatedTime(now);
        address.setProvinceName(districtService.getNameByCode(address.getProvinceCode()));
        address.setCityName(districtService.getNameByCode(address.getCityCode()));
        address.setAreaName(districtService.getNameByCode(address.getAreaCode()));
        int row=addressMapper.insert(address);
        if(row!=1)throw new AddNewAddressException("添加地址时出现未知错误");
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        List<Address> result=addressMapper.selectList(queryWrapper);
        for (Address address:result){
            address.setCreatedTime(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
            address.setCreatedUser(null);
        }
        return result;
    }
}
