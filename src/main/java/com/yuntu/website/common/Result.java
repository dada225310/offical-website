package com.yuntu.website.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(value= JsonInclude.Include.NON_NULL)
public class Result<T>{

    // 结果状态码
    private int errorcode;
    // 结果消息
    private String errormsg;
    // 结果数据
    private Object data;

    private String errdetail;


    public void setResultCode(ResultCode code) {
        this.errorcode = code.getCode();
        this.errormsg = code.getMessage();
    }
}