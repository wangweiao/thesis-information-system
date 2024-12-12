package hu.elte.thesis.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Data
public class Supervisor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "supervisor_sequence",
            sequenceName = "supervisor_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "supervisor_sequence"
    )
    private Long id;

    private String name;

    private String username;

    @Size(min = 6, max = 20)
    private String password;

    @Email
    private String email;

    private String faculty;

    @Override
    public String toString() {
        return "Supervisor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
