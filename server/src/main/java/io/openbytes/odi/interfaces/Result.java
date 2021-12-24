package io.openbytes.odi.interfaces;

import io.openbytes.odi.CodeAndMessage;
import lombok.Data;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

@Data
public class Result<T> {

    private Boolean success;

    private Integer httpCode;

    private String bizCode;

    private String message;

    private String traceId;

    private T data;

    private Result() {
    }

    public static <T> Result<T> ok() {
        return newInstance(CodeAndMessage.doSuccess);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = newInstance(CodeAndMessage.doSuccess);
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(CodeAndMessage codeAndMessage) {
        return newInstance(codeAndMessage);
    }

    public static <T> Result<T> error(CodeAndMessage codeAndMessage, String message) {
        Result<T> result = newInstance(codeAndMessage);
        result.message = message;
        return result;
    }

    private static <T> Result<T> newInstance(CodeAndMessage codeAndMessage) {
        Result<T> result = new Result<>();
        result.success = codeAndMessage.getSuccess();
        result.httpCode = codeAndMessage.getHttpCode();
        result.bizCode = codeAndMessage.getBizCode();
        result.message = codeAndMessage.getMessage();
        result.traceId = TraceContext.traceId();
        return result;
    }

}
