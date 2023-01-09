package KangWCB.comgram.ex.member;

public class MemberLoginEx extends RuntimeException{
    public MemberLoginEx() {
        super();
    }

    public MemberLoginEx(String message) {
        super(message);
    }

    public MemberLoginEx(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberLoginEx(Throwable cause) {
        super(cause);
    }

    protected MemberLoginEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
