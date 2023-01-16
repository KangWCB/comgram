package KangWCB.comgram.member.oauth;

import KangWCB.comgram.member.Member;
import KangWCB.comgram.member.MemberRepository;
import KangWCB.comgram.member.Role;
import KangWCB.comgram.photo.Photo;
import KangWCB.comgram.photo.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class UserOAuth2Service extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PhotoRepository photoRepository;

    private final PasswordEncoder passwordEncoder;

    public UserOAuth2Service(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String,Object> attributes = oAuth2User.getAttributes();
        Map<String,Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakao_account.get("email");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
//        String name = (String) properties.get("nickname");
        log.info("profile_img: {}", properties.get("profile_image"));
        log.info("properties: {}", properties);
        String profileURL = (String) properties.get("profile_image");

        if (memberRepository.existsByEmail(email)){
            log.info("Oauth로 로그인 한 적있는 유저");
        } else {
            String uuid = UUID.randomUUID().toString().substring(0, 6); // 임시 비밀번호 하나 만들어주기 로딩시에 필요함
            String uuid2 = UUID.randomUUID().toString().substring(0, 6); // 저장이름 하나 생성, 비밀번호랑 차별화
            Photo savedKakaoPhoto = savedKakaoPhoto(email, profileURL, uuid2);

            String nickname =  "new_user_"+uuid2;
            Member member = Member.builder()
                    .name(nickname)
                    .nickname(nickname)
                    .email(email)
                    .password(passwordEncoder.encode(uuid))
                    .photo(savedKakaoPhoto)
                    .role(Role.USER).build();

            memberRepository.save(member);
        }
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),attributes,"id");
    }

    private Photo savedKakaoPhoto(String email, String profileURL, String uuid2) {
        Photo photo = Photo.builder().savedPath(profileURL).savedNm(uuid2 + "." + email).build();
        Photo savedPhoto = photoRepository.save(photo);
        return savedPhoto;

    }


}