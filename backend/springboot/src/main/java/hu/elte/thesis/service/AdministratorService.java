package hu.elte.thesis.service;

import hu.elte.thesis.model.Administrator;
import hu.elte.thesis.repository.AdministratorRepository;
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
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Cacheable(value = "administrators", key = "#id")
    public Optional<Administrator> getAdministratorById(Long id) {
        return administratorRepository.findById(id); // Example user data
    }

    // Save a administrator and update cache with the new administrator data
    @CachePut(value = "administrators", key = "#administrator.id")
    public Administrator saveAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    // Evict the cache entry when a administrator is deleted
    @CacheEvict(value = "administrators", key = "#id")
    public void deleteAdministrator(Long id) {
        boolean exists = administratorRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Administrator with id " + id + " does not exist!");
        }
        administratorRepository.deleteById(id);
    }

    // Evict all cached entries in "administrators" when a bulk operation is done
    @CacheEvict(value = "administrators", allEntries = true)
    public void clearCache() {
        // Some bulk operation or cache clear logic
    }

    public List<Administrator> getAdministrators() {
        return administratorRepository.findAll();
    }

    public void addNewAdministrator(Administrator administrator) {
        Optional<Administrator> administratorOptional = administratorRepository.findAdministratorByUsername(administrator.getUsername());
        if (administratorOptional.isPresent()) {
            throw new IllegalStateException("Email taken!");
        }
        administratorRepository.save(administrator);
    }

    @Transactional
    public void updateAdministrator(Long administratorId, String name, String email) {
        Administrator administrator = administratorRepository.findById(administratorId)
                .orElseThrow(() -> new IllegalStateException("Administrator with id " + administratorId + " does not exist!"));

        if (name != null &&
                !name.isEmpty() &&
                !name.equals(administrator.getName())) {
            administrator.setName(name);
        }

        if (email != null &&
                !email.isEmpty() &&
                !email.equals(administrator.getEmail())) {
            Optional<Administrator> administratorOptional = administratorRepository.findAdministratorByEmail(email);
            if (administratorOptional.isPresent()) {
                throw new IllegalStateException("Email taken!");
            }
            administrator.setEmail(email);
        }
    }

    public Administrator authenticateForAdministrator(String username , String password){
        Administrator administrator = administratorRepository.findAdministratorByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Administrator with username '%s' does not exist.", username)));

//        if (!administrator.isActive()){
//            throw new IllegalStateException("driver is not active");
//        }

        if (!administrator.getPassword().equals(password)){
            throw new IllegalArgumentException("The provided username or password does not match.");
        }

        return administrator;
    }

}
