package SpringBootShop.project.dto.user;

public class LoginResponse {
    private String grantType;
    private String accessToken;

    public LoginResponse(String grantType, String accessToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
