package com.yuntu.website.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 *
 * @author dada
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser {
    @TableId(value ="id", type = IdType.AUTO)
    private String id;

    private String userName;

    private String password;

    @TableField(exist = false)
    private String qrCode;

    /**
     * 创建时间
     */

    private Date createTime;

    private Date updateTime;

}

