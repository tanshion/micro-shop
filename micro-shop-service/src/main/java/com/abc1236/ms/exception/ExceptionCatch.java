package com.abc1236.ms.exception;


import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.core.result.ResultStatus;
import com.abc1236.ms.util.HttpUtil;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tanshion
 * @email 843565561@qq.com
 * 统一异常捕获类
 **/
@ControllerAdvice
@Slf4j
@ConditionalOnWebApplication
public class ExceptionCatch {

    //捕获ServiceException此类异常
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResultEntity<Object> ServiceException(ServiceException exception) {
        ResultEntity<Object> resultEntity = exception.getResultData();
        return getResultData(resultEntity, exception);
    }

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity<Object> exception(Exception exception) {
        log.error("异常堆栈信息: ", exception);
        return ResultEntity.fail();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(NoHandlerFoundException exception) {
        ResultEntity<Object> resultEntity = new ResultEntity<>(ResultStatus.NOT_FOUND);
        return getResultData(resultEntity, exception);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    private ResultEntity<Object> exceptionHandle(AccessDeniedException exception, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StrUtil.isEmpty(authorization) || "Bearer".equals(authorization.trim())) {
            ResultEntity<Object> resultEntity = ResultEntity.fail(ResultStatus.UNAUTHENTICATED);
            return getResultData(resultEntity, exception);
        }
        if (null == HttpUtil.getJwtUser()) {
            ResultEntity<Object> resultEntity = ResultEntity.fail(ResultStatus.ACCESS_TOKEN_EXPIRED);
            return getResultData(resultEntity, exception);
        }
        ResultEntity<Object> resultEntity = ResultEntity.fail(ResultStatus.UNAUTHORISED);
        return getResultData(resultEntity, exception);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(InvalidTokenException exception) {
        ResultStatus resultStatus = ResultStatus.ACCESS_TOKEN_EXPIRED;
        ResultEntity<Object> resultEntity = new ResultEntity<>(resultStatus);
        return getResultData(resultEntity, exception);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(ValidationException exception) {
        String message = Splitter.on(",")
            .splitToList(exception.getMessage())
            .stream()
            .map(s -> Splitter.on(":").omitEmptyStrings().trimResults().splitToList(s).get(1))
            .collect(Collectors.joining(","));
        ResultEntity<Object> resultEntity = ResultEntity.fail(ResultStatus.INVALID_PARAM).message(message);
        return getResultData(resultEntity, exception);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return BindingResult(exception, bindingResult);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return BindingResult(exception, bindingResult);
    }

    private ResultEntity<Object> BindingResult(Exception exception, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String message = fieldErrors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
        ResultEntity<Object> resultEntity = ResultEntity.fail(ResultStatus.INVALID_PARAM).message(message);
        return getResultData(resultEntity, exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        String message = violations.stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(","));
        ResultEntity<Object> resultEntity = ResultEntity.fail(ResultStatus.INVALID_PARAM).message(message);
        return getResultData(resultEntity, exception);
    }


    @ExceptionHandler({MissingServletRequestParameterException.class, MissingPathVariableException.class})
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(ServletRequestBindingException exception) {
        ResultEntity<Object> resultEntity = new ResultEntity<>(ResultStatus.MISSING_SERVLET_REQUEST_PARAMETER);
        return getResultData(resultEntity, exception);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(TypeMismatchException exception) {
        ResultEntity<Object> resultEntity = new ResultEntity<>(ResultStatus.TYPE_MISMATCH);
        return getResultData(resultEntity, exception);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResultEntity<Object> exceptionHandle(HttpRequestMethodNotSupportedException exception) {
        ResultEntity<Object> resultEntity = new ResultEntity<>(ResultStatus.REQUEST_METHOD_NOT_SUPPORTED);
        return getResultData(resultEntity, exception);
    }


    @ExceptionHandler({
        HttpMessageNotReadableException.class,
        ServletRequestBindingException.class
    })
    @ResponseBody
    public ResultEntity<Object> requestParamExceptionHandle(Exception exception) {
        ResultEntity<Object> resultEntity = new ResultEntity<>(ResultStatus.INVALID_PARAM);
        return getResultData(resultEntity, exception);
    }


    ResultEntity<Object> getResultData(ResultEntity<Object> resultEntity, Exception exception) {
        String exceptionMessage = exception.getMessage();
        log.warn("异常信息: {}", exceptionMessage);
        if (exception instanceof ServiceException) {
            resultEntity.message(exceptionMessage);
        } else {
            resultEntity.message(resultEntity.getMsg() + " @Error: " + exceptionMessage);
        }
        return resultEntity;
    }
}
