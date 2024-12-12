package hu.elte.thesis.controller;

import hu.elte.thesis.model.Administrator;
import hu.elte.thesis.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/administrator")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @GetMapping
    public List<Administrator> getAdministrators() {
        return administratorService.getAdministrators();
    }

    @GetMapping("/{id}")
    public Optional<Administrator> getAdministrator(@PathVariable Long id) {
        return administratorService.getAdministratorById(id);
    }

    @PostMapping
    public Administrator createAdministrator(@RequestBody Administrator administrator) {
        return administratorService.saveAdministrator(administrator);
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrator(@PathVariable Long id) {
        administratorService.deleteAdministrator(id);
    }

    @PutMapping(path = "{administratorId}")
    public void updateAdministrator(
            @PathVariable("administratorId") Long administratorId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        administratorService.updateAdministrator(administratorId, name, email);
    }
}
