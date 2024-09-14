package com.cy.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.DistrictService;
import com.cy.store.service.ex.getNameByCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        QueryWrapper<District> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent",parent);
        List<District> result=districtMapper.selectList(queryWrapper);
        for(District district:result){
            district.setId(null);
            district.setParent(null);
        }
        return result;
    }

    @Override
    public String getNameByCode(String code) {
        QueryWrapper<District> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",code);
        List<District> result=districtMapper.selectList(queryWrapper);
        if(result.size()>1)throw new getNameByCodeException("查找行政区时出现未知错误");
        String sResult=result.get(0).getName();
        return sResult;
    }
}
