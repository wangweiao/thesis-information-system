package hu.elte.thesis.repository;

import hu.elte.thesis.model.Supervisor;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    @Query("SELECT s FROM Supervisor s WHERE s.username = ?1")
    Optional<Supervisor> findSupervisorByUsername(String username);

    @Query("SELECT s FROM Supervisor s WHERE s.email = ?1")
    Optional<Supervisor> findSupervisorByEmail(@Email String email);

}
