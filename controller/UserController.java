package SpringBootShop.project.controller;

import SpringBootShop.project.domain.User;
import SpringBootShop.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/signUp")
    @ResponseBody()
    public User signUp(@RequestBody UserForm form) {
        return userService.singUp(form.getUserId(), form.getUserPw());
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    @ResponseBody()
    public User login(@RequestBody UserForm form) {
        return userService.login(form.getUserId(), form.getUserPw());
    }

    /**
     * 로그 아웃
     */
    @GetMapping("/logout")
    @ResponseBody()
    public String logout() {
        userService.logout();
        return "로그아웃 완료";
    }

    /**
     * 유저 정보 가져오기 - id
     */
    @GetMapping("/users/{id}")
    @ResponseBody()
    public User findById(@PathVariable String id) {
        return userService.findByUserId(id);
    }

    /**
     * 전체 유저 가져오기
     */
    @GetMapping("/users")
    @ResponseBody()
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     * 비밀번호 수정
     */
    @PutMapping("/users/{id}")
    @ResponseBody()
    public String updatePw(@PathVariable String id, @RequestBody UserForm form) {
        userService.updatePw(id, form);
        return "변경완료되었습니다";
    }
}
