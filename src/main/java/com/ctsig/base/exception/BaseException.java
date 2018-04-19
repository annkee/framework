package com.ctsig.base.exception;

import com.ctsig.base.enums.ResultEnum;
import lombok.Data;

/**
 * 异常处理
 *
 * @author wangan
 * @date 2017/1/5
 */
@Data
public class BaseException extends RuntimeException {

    private ResultEnum resultEnum;

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

    public ResultEnum getResultEnum() {
        return resultEnum;
    }


}
