package SpringBootShop.project.controller;

import SpringBootShop.project.domain.User;
import SpringBootShop.project.dto.user.LoginResponse;
import SpringBootShop.project.dto.user.UserForm;
import SpringBootShop.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    // 의존성 받기
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원 가입
     */
    @PostMapping("/api/auth/signup")
    @ResponseBody()
    public User signUp(@RequestBody UserForm form) {
        return userService.singUp(form.getUserId(), form.getUserPw());
    }

    /**
     * 로그인
     */
    @PostMapping("/api/auth/login")
    @ResponseBody()
    public LoginResponse login(@RequestBody UserForm form) {
        return userService.login(form.getUserId(), form.getUserPw());
    }

    /**
     * 유저 정보 가져오기 - id
     */
    @GetMapping("/api/users/{id}")
    @ResponseBody()
    public User findById(@PathVariable String id) {
        return userService.findByUserId(id);
    }

    /**
     * 전체 유저 가져오기
     */
    @GetMapping("/api/users")
    @ResponseBody()
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/api/users/me/password")
    @ResponseBody()
    public String updatePw(Authentication authentication, @RequestBody UserForm form) {
        String userId = authentication.getName();

        userService.updatePw(userId, form);
        return "변경완료되었습니다";
    }
}
