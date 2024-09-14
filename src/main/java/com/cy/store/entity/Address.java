package com.cy.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@TableName("t_address")
public class Address extends BaseEntity implements Serializable {
    @TableId(value = "aid",type = IdType.AUTO)
    private Integer aid;
    @TableField("uid")
    private Integer uid;
    @TableField("name")
    private String name;
    @TableField("province_name")
    private String provinceName;
    @TableField("province_code")
    private String provinceCode;
    @TableField("city_name")
    private String cityName;
    @TableField("city_code")
    private String cityCode;
    @TableField("area_name")
    private String areaName;
    @TableField("area_code")
    private String areaCode;
    @TableField("zip")
    private String zip;
    @TableField("address")
    private String address;
    @TableField("phone")
    private String phone;
    @TableField("tel")
    private String tel;
    @TableField("tag")
    private String tag;
    @TableField("is_default")
    private Integer isDefault;
}
