package KangWCB.comgram.member;

public enum Role {
    USER("유저"),ADMIN("관리자"),DEL("삭제");
    private final String role;
    Role(String role) {
        this.role = role;
    }
}
