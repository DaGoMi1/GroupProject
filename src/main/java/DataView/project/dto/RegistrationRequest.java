package DataView.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String username;
    private String password;
    private String password2;
    private String name;
    private String email;
}
