package KangWCB.comgram.ex;

public class JWTVerificationException extends RuntimeException {

    public JWTVerificationException() {
        super();
    }

    public JWTVerificationException(String message) {
        super(message);
    }

    public JWTVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTVerificationException(Throwable cause) {
        super(cause);
    }

    protected JWTVerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
