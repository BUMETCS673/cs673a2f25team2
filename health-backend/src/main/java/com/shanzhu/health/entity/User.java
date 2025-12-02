package com.shanzhu.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("j_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer status;
    private String avatar;
    private Integer deleted;
    private String paymentStatus;
    private LocalDateTime accessExpiry;

    @TableField(exist = false)
    private List<Integer> roleIdList;

    @TableField(exist = false)
    private String newPassword;

}
