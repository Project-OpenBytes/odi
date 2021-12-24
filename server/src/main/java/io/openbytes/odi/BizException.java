package io.openbytes.odi;

public class BizException extends RuntimeException {

    private final CodeAndMessage codeAndMessage;

    private final String exceptionMessage;

    public BizException(CodeAndMessage codeAndMessage) {
        this.codeAndMessage = codeAndMessage;
        this.exceptionMessage = codeAndMessage.getMessage();
    }

    public BizException(CodeAndMessage codeAndMessage, String exceptionMessage) {
        this.codeAndMessage = codeAndMessage;
        this.exceptionMessage = exceptionMessage;
    }

    public CodeAndMessage getCodeAndMessage() {
        return codeAndMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

}
