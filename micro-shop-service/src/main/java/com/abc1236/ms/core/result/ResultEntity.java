package com.abc1236.ms.core.result;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * @author tanshion
 * @email 843565561@qq.com
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResultEntity<T> implements Serializable {

    private boolean success;
    private Integer code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traceId;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    //private Long total;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResultEntity(ResultStatus status) {
        this(null, status);
    }

    public ResultEntity(T data, ResultStatus status) {
        this.data = data;
        this.code = status.code();
        this.msg = status.message();
        this.success = ResultStatus.SUCCESS == status;
        this.traceId = MDC.get("traceId");
    }

    public static <T> ResultEntity<T> success() {
        return new ResultEntity<>(null, ResultStatus.SUCCESS);
    }

    public static <T> ResultEntity<T> success(T result) {
        return new ResultEntity<>(result, ResultStatus.SUCCESS);
    }

    public static <T> ResultEntity<Page<T>> success(Page<T> page) {
        return new ResultEntity<>(page,ResultStatus.SUCCESS);
    }

    public static <T> ResultEntity<T> fail() {
        return new ResultEntity<>(ResultStatus.FAIL);
    }

    public static <T> ResultEntity<T> fail(ResultStatus status) {
        return new ResultEntity<>(status);
    }

    public ResultEntity<T> message(String message) {
        this.msg = message;
        return this;
    }
}
