package com.ctsig.base.exception;

import com.ctsig.base.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常配置
 *
 * @author wangan
 * @date 2018/4/1
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResultVO handleException(BaseException e) {

        log.info("handleException: e={}", e.getMessage());

        return new ResultVO(e.getResultCodeConstant());
    }

}
