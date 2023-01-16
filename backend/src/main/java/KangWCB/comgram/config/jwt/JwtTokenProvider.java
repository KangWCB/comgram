package KangWCB.comgram.config.jwt;

import KangWCB.comgram.config.jwt.dto.TokenInfo;
import KangWCB.comgram.ex.JWTVerificationException;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.member.MemberService;
import KangWCB.comgram.member.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    private String secretKey = "test";

    private final MemberService memberService;

    // 토큰 유효시간 30분
    private final long tokenValidTime = 30 * 60 * 1000L;

    // refresh 토큰 유효시간
    private final long refreshTokenValidTime = 24 * 60 * 60 * 1000L;

    private final SecurityUserDetailService userDetailsService;
    private final MemberRepository memberRepository;

    // 객체 초기화, secretKey를 Base64로 인코딩
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    // JWT 토큰 생성
    public TokenInfo createToken(String platform, String userPK, Role roles) {
        Claims claims = Jwts.claims().setSubject(userPK); // JWT payload에 저장되는 정보 단위
        claims.put("roles", roles); // 정보 저장 (key-value)
        claims.put("platform", platform);
        Date now = new Date();
        // Access Token 생성
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 세팅
                .compact();

        String refreshToken = makeRefreshToken(userPK, now);

        memberService.updateRefreshToken(userPK, refreshToken); // 해당 유저에 refresh 토큰 저장

        // access 토큰과 Refresh 토큰 모두 담은 Info 생성
        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String makeRefreshToken(String userPk, Date now) {
        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setClaims(Jwts.claims().setSubject(userPk))
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 세팅
                .compact();
        return refreshToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        String userPK = this.getUserPK(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPK);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPK(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization": "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return bearerToken;
    }

    // 토큰의 유효성 + 만료일자 확인
    public JwtCode validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken); // 내가 토큰을 서버에서 사용하겠다.
            return JwtCode.ACCESS;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return JwtCode.EXPIRED;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("jwtException : {}", e);
        }
        return JwtCode.DENIED;
    }

    /**
     * token refresh 짜기
     * access 토큰 만료되면 발급
     * 그리고 refresh도 검증
     *
     * @param request
     * @return
     */
    @Transactional
    public TokenInfo refresh(HttpServletRequest request) {

        // === Refresh Token 유효성 검사 === //
        String refreshToken = request.getHeader("RefreshToken");

        // === Access Token 재발급 === //
        Date now = new Date();
        String username = getUserPK(refreshToken); // 여기서 유저 이메일을 들고온다.

        // refresh 토큰에서 찾은 정보를 바탕으로 유저를 찾는다.
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
        }

        Claims claims = Jwts.claims().setSubject(username); // JWT payload에 저장되는 정보 단위
        claims.put("roles", Role.USER); // 정보 저장 (key-value)

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 세팅
                .compact();

        Date refreshExpire = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody().getExpiration();
        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        // === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
        long refreshExpireTime = refreshExpire.getTime() * 1000L;
        long diffMin = (refreshExpireTime - now.getTime()) / 1000 / 60;
        if (diffMin < 5) {
            refreshToken = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                    .signWith(SignatureAlgorithm.HS256, secretKey) // 사용할 암호화 알고리즘과 signature에 들어갈 secret 값 세팅
                    .compact();
            member.registerRefreshToken(refreshToken);
        }
            return TokenInfo.builder()
                    .accessToken(accessToken)
                    .grantType("Bearer")
                    .refreshToken(refreshToken)
                    .build();
    }


}
