package com.cy.store.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BaseEntity implements Serializable {
    @TableField("created_user")
    private String createdUser;
    @TableField("created_time")
    private Date createdTime;
    @TableField("modified_user")
    private String modifiedUser;
    @TableField("modified_time")
    private Date modifiedTime;


}
