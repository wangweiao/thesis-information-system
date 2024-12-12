package hu.elte.thesis.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdministratorPostDTO {

    private Long id;

    private String name;

    @Email
    private String email;

    private String faculty;

    private String username;

    @Size(min = 6, max = 20)
    private String password;

}
