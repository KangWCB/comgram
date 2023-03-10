package KangWCB.comgram.config.jwt;

import KangWCB.comgram.config.jwt.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT를 받아옴
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        // 유효한 토큰인지 확인
        if (token != null && getJwtCode(token) == JwtCode.ACCESS) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옴
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            log.info("유저정보는 받아옴 : {}",token);
            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else if (token != null && getJwtCode(token) == JwtCode.EXPIRED){
            log.info("accessToken 토큰 재발급 필요함");
            TokenInfo refresh = jwtTokenProvider.refresh((HttpServletRequest) request);
        }
        log.info("header에 jwt부재");
        chain.doFilter(request, response);
    }

    private JwtCode getJwtCode(String token) {
        return jwtTokenProvider.validateToken(token);
    }


}
