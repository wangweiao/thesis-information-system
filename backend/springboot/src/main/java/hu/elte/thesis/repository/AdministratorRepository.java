package hu.elte.thesis.repository;

import hu.elte.thesis.model.Administrator;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    @Query("SELECT s FROM Administrator s WHERE s.username = ?1")
    Optional<Administrator> findAdministratorByUsername(String username);

    @Query("SELECT s FROM Administrator s WHERE s.email = ?1")
    Optional<Administrator> findAdministratorByEmail(@Email String email);

}
