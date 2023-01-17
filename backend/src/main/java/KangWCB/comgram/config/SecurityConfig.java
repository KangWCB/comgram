package KangWCB.comgram.config;

import KangWCB.comgram.config.jwt.JwtAuthenticationFilter;
import KangWCB.comgram.config.jwt.JwtTokenProvider;
import KangWCB.comgram.member.oauth.CustomOAuth2UserService;
import KangWCB.comgram.member.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//    private final UserOAuth2Service userOAuth2Service;
    private final CustomOAuth2UserService customOAuth2UserService;
    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 만을 고려하여 기본설정 해제
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함
                .and()
                .authorizeRequests() // 요청에 대한 사용 권한 체크
                .antMatchers("/admin/**").hasRole("ADMIN")// admin
                .antMatchers("/api/members/info", "/api/board/**","/api/search/**","/api/message/**","/api/follow/**").authenticated()// 로그인을 제외한 모든 기능은 인증을 거쳐야함.
                .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)// JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣음;
                .oauth2Login() // 여기서부터 Oauth 설정
                .defaultSuccessUrl("/login-success") // 성공하면
                .successHandler(oAuth2AuthenticationSuccessHandler)//사용자 정의 로직을 실행
                .userInfoEndpoint() // 로그인이 성공하면 해당 유저의 정보를 들고 customOauth에서 후처리를 해주겠다는 의미
                .userService(customOAuth2UserService);

        return http.build();
    }

}
