package KangWCB.comgram.member.oauth;

import KangWCB.comgram.config.jwt.JwtTokenProvider;
import KangWCB.comgram.config.jwt.dto.TokenInfo;
import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.member.Role;
import KangWCB.comgram.photo.Photo;
import KangWCB.comgram.photo.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PhotoRepository photoRepository;
    private PasswordEncoder passwordEncoder;

    @Value("${front-end.ip}")
    String ip;
    public OAuth2AuthenticationSuccessHandler (@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("Principal user attribute {}",attributes);
        Long memberId;
        if(memberRepository.findByEmail(oAuth2User.getName()).isEmpty()){
            String uuid = UUID.randomUUID().toString().substring(0, 6); // ?????? ???????????? ?????? ??????????????? ???????????? ?????????
            String uuid2 = UUID.randomUUID().toString().substring(0, 6); // ???????????? ?????? ??????, ??????????????? ?????????
            Photo savedPhoto = null;
            String nickname =  "new_user_"+uuid2;
            if (attributes.get("picture") != null){
                savedPhoto = savedPhoto(oAuth2User.getName(), (String)attributes.get("picture"), uuid2);
            }
            if (attributes.get("nickname") != null){
                nickname = (String)attributes.get("nickname");
            }
            Member member = Member.builder()
                    .name((String) attributes.get("name"))
                    .nickname(nickname)
                    .email(oAuth2User.getName())
                    .password(passwordEncoder.encode(uuid))
                    .photo(savedPhoto)
                    .role(Role.USER).build();
            Member saveMember = memberRepository.save(member);
            memberId = saveMember.getId();
        }
        else{
            memberId = memberRepository.findByEmail(oAuth2User.getName()).get().getId();
        }
        // ???????????? ?????? ??????
        TokenInfo token = jwtTokenProvider.createToken("oauth", oAuth2User.getName(), Role.USER);
        String jwt = token.getAccessToken();
        String url = makeRedirectUrl(jwt,memberId);
        System.out.println("url: " + url);
        if (response.isCommitted()) {

            logger.debug("????????? ?????? ????????? ???????????????. " + url + "??? ???????????????????????? ?????? ??? ????????????.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }
    private String makeRedirectUrl(String token, Long memberId) {
        return UriComponentsBuilder.fromUriString("http://"+ip+"/oauth2/redirect/?token="+token+"&id="+memberId)
                .build().toUriString();
    }

    private Photo savedPhoto(String email, String profileURL, String uuid2) {
        Photo photo = Photo.builder().savedPath(profileURL).savedNm(uuid2 + "." + email).build();
        Photo savedPhoto = photoRepository.save(photo);
        return savedPhoto;
    }
}

