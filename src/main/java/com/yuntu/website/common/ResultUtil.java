package com.yuntu.website.common;

public class ResultUtil {

    /**
     * 成功无返回数据
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.OK);
        return result;
    }

    /**
     * 成功带返回数据
     * @param data 响应数据
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.OK);
        result.setData(data);
        return result;
    }

    /**
     * 自定义状态码并带返回数据
     * @param code 结果状态码
     * @param data 响应数据
     * @return
     */
    public static Result success(ResultCode code, Object data) {
        Result result = new Result();
        result.setResultCode(code);
        result.setData(data);
        return result;
    }

    /**
     * 失败带返回数据
     * @param data 响应数据
     * @return
     */
    public static Result failure(ResultCode code, Object data) {
        Result result = new Result();
        result.setResultCode(code);
        result.setData(data);
        return result;
    }

    public static Result failure(ResultCode code) {
        Result result = new Result();
        result.setResultCode(code);
        return result;
    }


}
