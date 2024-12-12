package hu.elte.thesis.controller;

import hu.elte.thesis.model.Supervisor;
import hu.elte.thesis.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/supervisor")
public class SupervisorController {

    private final SupervisorService supervisorService;

    @Autowired
    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping
    public List<Supervisor> getSupervisors() {
        return supervisorService.getSupervisors();
    }

    @GetMapping("/{id}")
    public Optional<Supervisor> getSupervisor(@PathVariable Long id) {
        return supervisorService.getSupervisorById(id);
    }

    @PostMapping
    public Supervisor createSupervisor(@RequestBody Supervisor supervisor) {
        return supervisorService.saveSupervisor(supervisor);
    }

    @DeleteMapping("/{id}")
    public void deleteSupervisor(@PathVariable Long id) {
        supervisorService.deleteSupervisor(id);
    }

    @PutMapping(path = "{supervisorId}")
    public void updateSupervisor(
            @PathVariable("supervisorId") Long supervisorId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        supervisorService.updateSupervisor(supervisorId, name, email);
    }
}
