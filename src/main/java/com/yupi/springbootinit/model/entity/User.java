package com.yupi.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * account
     */
    private String userAccount;

    /**
     * password
     */
    private String userPassword;

    /**
     * platform id
     */
    private String unionId;

    /**
     * open id
     */
    private String mpOpenId;

    /**
     * nickname
     */
    private String userName;

    /**
     * avatar
     */
    private String userAvatar;

    /**
     * introduction
     */
    private String userProfile;

    /**
     * user role
     */
    private String userRole;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * if deleted
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}