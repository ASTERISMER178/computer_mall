package com.cy.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_dict_district")
@EqualsAndHashCode
public class District {
    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;
    @TableField("parent")
    private String parent;
    @TableField("code")
    private String code;
    @TableField("name")
    private String name;
}
