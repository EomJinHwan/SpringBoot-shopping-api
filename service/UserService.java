package SpringBootShop.project.service;

import SpringBootShop.project.domain.User;
import SpringBootShop.project.dto.user.LoginResponse;
import SpringBootShop.project.dto.user.UserForm;
import SpringBootShop.project.repository.UserRepository;
import SpringBootShop.project.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private UserRepository repository;
    private JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 회원 가입 - save() 사용
    public User singUp(String id, String pw) {
        checkId(id);
        checkPw(pw);
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(pw);
        User user = new User(id, encodedPassword);

        return repository.save(user);
    }

    // 아이디 검사
    public void checkId(String id) {
        if (id.length() < 4 || id.length() > 7) {
            throw new IllegalArgumentException("아이디는 4~7글자로 해주세요");
        }
        validateDuplicatedUser(id);
    }

    // 아이디 중복 검사 - findByUserId로 변경
    private void validateDuplicatedUser(String id) {
        repository.findByUserId(id).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 아이디 입니다");
        });
    }

    // 비밀번호 검사
    public void checkPw(String pw) {
        if (pw.length() < 5) {
            throw new IllegalArgumentException("비밀번호를 5자리 이상으로 해주세요");
        }
    }

    // 로그인
    public LoginResponse login(String id, String pw) {
        User user = repository.findByUserId(id).
                orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다"));
        if (!passwordEncoder.matches(pw, user.getUserPw())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다");
        }
        String accessToken = jwtTokenProvider.createAccessToken(
                user.getUserId(),
                user.getRole());
        return new LoginResponse("Bearer", accessToken);
    }


    // 유저 정보 가져오기 - id - findByUserId로 변경
    public User findByUserId(String id) {
        return repository.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("조건에 맞는 사용자가 없습니다"));
    }

    // 전체 유저 정보 가져오기
    public List<User> findAll() {
        return repository.findAll();
    }

    // 비밀번호 변경
    public User updatePw(String id, UserForm form) {
        User user = findByUserId(id);
        // 비밀번호 암호화 추가
        user.setUserPw(passwordEncoder.encode(form.getUserPw()));
        return user;
    }
}
