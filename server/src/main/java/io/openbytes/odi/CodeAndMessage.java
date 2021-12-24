package io.openbytes.odi;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http code & biz code
 */
@Getter
@AllArgsConstructor
public enum CodeAndMessage {
    doSuccess(true, 200, "odi-1000000", "Success."),

    doFailed(false, 200, "odi-1000001", "Failed."),

    argsIllegal(false, 200, "odi-10000011", "Args illegal."),
    ;

    private final Boolean success;

    private final Integer httpCode;

    private final String bizCode;

    private final String message;

}
