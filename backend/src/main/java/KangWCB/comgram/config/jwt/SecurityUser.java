package KangWCB.comgram.config.jwt;

import KangWCB.comgram.member.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private final Member member;

    public SecurityUser(Member member) {
        super(member.getId().toString(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getRole().toString()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}