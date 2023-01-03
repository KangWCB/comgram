package KangWCB.comgram.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 로그인한 사용자를 등록자 및 수정자로 지정하기 위해 AuditorAware 인터페이스 구현체
 */
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // 현재 로그인한 사용자의 정보 추출
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication != null){
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}
