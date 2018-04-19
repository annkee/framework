package com.ctsig.base.utils;

import com.ctsig.base.enums.ResultEnum;
import com.ctsig.base.vo.ResultVO;


/**
 * 返回结果格式
 *
 * @author wangan
 * @date 2017/1/5
 */
public class ResultVoUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO(ResultEnum.Success, object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }

}
