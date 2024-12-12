package hu.elte.thesis.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDTO {

    private Long id;

    private String name;

    @Email
    private String email;

    private String faculty;

    @Size(min = 6, max = 20)
    private String username;

}
