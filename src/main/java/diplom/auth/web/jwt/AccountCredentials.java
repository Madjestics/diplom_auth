package diplom.auth.web.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountCredentials {

    @Schema(description = "Логин профиля (для админа - ADMIN)")
    private String login;

    @Schema(description = "Пароль профиля (для админа - admin)")
    private String password;

    public AccountCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
