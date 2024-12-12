package hu.elte.thesis.service;

import hu.elte.thesis.model.Supervisor;
import hu.elte.thesis.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    @Autowired
    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    @Cacheable(value = "supervisors", key = "#id")
    public Optional<Supervisor> getSupervisorById(Long id) {
        return supervisorRepository.findById(id); // Example user data
    }
    
    @CachePut(value = "supervisors", key = "#supervisor.id")
    public Supervisor saveSupervisor(Supervisor supervisor) {
        return supervisorRepository.save(supervisor);
    }

    // Evict the cache entry when a supervisor is deleted
    @CacheEvict(value = "supervisors", key = "#id")
    public void deleteSupervisor(Long id) {
        boolean exists = supervisorRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Supervisor with id " + id + " does not exist!");
        }
        supervisorRepository.deleteById(id);
    }
    
    @CacheEvict(value = "supervisors", allEntries = true)
    public void clearCache() {
        // Some bulk operation or cache clear logic
    }

    public List<Supervisor> getSupervisors() {
        return supervisorRepository.findAll();
    }

    public void addNewSupervisor(Supervisor supervisor) {
        Optional<Supervisor> supervisorOptional = supervisorRepository.findSupervisorByUsername(supervisor.getUsername());
        if (supervisorOptional.isPresent()) {
            throw new IllegalStateException("Email taken!");
        }
        supervisorRepository.save(supervisor);
    }

    @Transactional
    public void updateSupervisor(Long supervisorId, String name, String email) {
        Supervisor supervisor = supervisorRepository.findById(supervisorId)
                .orElseThrow(() -> new IllegalStateException("Supervisor with id " + supervisorId + " does not exist!"));

        if (name != null &&
                !name.isEmpty() &&
                !name.equals(supervisor.getName())) {
            supervisor.setName(name);
        }

        if (email != null &&
                !email.isEmpty() &&
                !email.equals(supervisor.getEmail())) {
            Optional<Supervisor> supervisorOptional = supervisorRepository.findSupervisorByEmail(email);
            if (supervisorOptional.isPresent()) {
                throw new IllegalStateException("Email taken!");
            }
            supervisor.setEmail(email);
        }
    }

    public Supervisor authenticateForSupervisor(String username , String password){
        Supervisor supervisor = supervisorRepository.findSupervisorByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Supervisor with username '%s' does not exist.", username)));

//        if (!supervisor.isActive()){
//            throw new IllegalStateException("driver is not active");
//        }

        if (!supervisor.getPassword().equals(password)){
            throw new IllegalArgumentException("The provided username or password does not match.");
        }

        return supervisor;
    }

}

