package hu.elte.thesis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequestDTO {

    @NotNull
    @Email
    private String email;

    @NotBlank
    private String name;

    private String username;

    private String password;

}
