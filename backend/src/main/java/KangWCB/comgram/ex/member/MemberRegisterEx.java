package KangWCB.comgram.ex.member;

public class MemberRegisterEx extends RuntimeException{

    public MemberRegisterEx() {
        super();
    }

    public MemberRegisterEx(String message) {
        super(message);
    }

    public MemberRegisterEx(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberRegisterEx(Throwable cause) {
        super(cause);
    }

    protected MemberRegisterEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
