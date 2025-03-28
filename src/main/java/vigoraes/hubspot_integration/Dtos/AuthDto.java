package vigoraes.hubspot_integration.Dtos;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthDto {

    @NotNull(message = "Usuário não pode ser nulo")
    @Size(min = 5, max = 20, message = "Usuário deve ter de 8 a 20 caracteres")
    private String username;

    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 8, message = "Senha deve ter ao menos  caracteres")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
