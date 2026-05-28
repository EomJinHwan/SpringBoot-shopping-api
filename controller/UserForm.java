package SpringBootShop.project.controller;

public class UserForm {
    private String userId;
    private String userPw;

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
}
