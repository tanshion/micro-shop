package com.abc1236.ms.exception;


import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.core.result.ResultStatus;

/**
 * @author tanshion
 * @email 843565561@qq.com
 * 自定义异常类型
 **/
public class ServiceException extends RuntimeException {

    //错误代码
    ResultEntity<Object> resultEntity;

    public ServiceException(String message) {
        super(message);
        resultEntity = ResultEntity.fail().message(message);
    }

    public ServiceException(ResultStatus status) {
        super(status.message());
        ResultEntity.fail(status);
    }

    public ServiceException(ResultStatus status, String message) {
        super(message);
        ResultEntity.fail(status).message(message);
    }

    public ResultEntity<Object> getResultData() {
        return resultEntity;
    }
}
