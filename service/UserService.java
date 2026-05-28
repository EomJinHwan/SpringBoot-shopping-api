package SpringBootShop.project.service;

import SpringBootShop.project.controller.UserForm;
import SpringBootShop.project.domain.User;
import SpringBootShop.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private UserRepository repository;
    private User loginUser = null;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // 회원 가입 - save() 사용
    public User singUp(String id, String pw) {
        checkId(id);
        checkPw(pw);
        User user = new User(id, pw);
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
    public User login(String id, String pw) {
        User user = repository.findByUserId(id).
                orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다"));
        if (!user.getUserPw().equals(pw)) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다");
        }
        loginUser = user;
        return user;
    }

    // 로그 아웃
    public User logout() {
        if (loginUser == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다");
        }
        loginUser = null;
        return null;
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

        user.setUserPw(form.getUserPw());
        return user;
    }
}
