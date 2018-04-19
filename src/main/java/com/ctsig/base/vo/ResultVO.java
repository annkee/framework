package com.ctsig.base.vo;

import com.ctsig.base.enums.ResultEnum;
import lombok.Data;

/**
 * 最外层返回格式控制,给前端返回的数据格式化
 *
 * @author wangan
 * @date 2017/1/4
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 具体内容
     */
    private T data;

    public ResultVO() {
    }

    public ResultVO(ResultEnum resultEnum, T data) {
        this(resultEnum);
        this.data = data;
    }

    public ResultVO(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }
}
