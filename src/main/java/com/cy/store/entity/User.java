package com.cy.store.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@TableName("t_user")
public class User extends BaseEntity implements Serializable {
        @TableId(value = "uid",type = IdType.AUTO)
        private long uid;
        @TableField("username")
        private String username;
        @TableField("password")
        private String password;
        @TableField("salt")
        private String salt;
        @TableField("phone")
        private String phone;
        @TableField("email")
        private String email;
        @TableField("gender")
        private Integer gender;
        @TableField("avatar")
        private String avatar;
        @TableLogic("is_delete")
        private Integer isDelete;

}
