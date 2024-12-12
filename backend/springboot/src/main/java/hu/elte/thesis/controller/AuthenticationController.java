package hu.elte.thesis.controller;

import hu.elte.thesis.dto.request.AuthRequestDTO;
import hu.elte.thesis.dto.response.AdministratorDTO;
import hu.elte.thesis.dto.response.StudentDTO;
import hu.elte.thesis.dto.response.SupervisorDTO;
import hu.elte.thesis.mapper.AdministratorMapper;
import hu.elte.thesis.mapper.StudentMapper;
import hu.elte.thesis.mapper.SupervisorMapper;
import hu.elte.thesis.model.Administrator;
import hu.elte.thesis.model.Student;
import hu.elte.thesis.model.Supervisor;
import hu.elte.thesis.service.AdministratorService;
import hu.elte.thesis.service.StudentService;
import hu.elte.thesis.service.SupervisorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/login")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final StudentService studentService;

    private final StudentMapper studentMapper;

    private final SupervisorService supervisorService;

    private final SupervisorMapper supervisorMapper;

    private final AdministratorService administratorService;

    private final AdministratorMapper administratorMapper;

    @PostMapping(path = "student")
    public StudentDTO loginAsStudent(@RequestBody AuthRequestDTO request){
        Student student = studentService.authenticateForStudent(request.getUsername(), request.getPassword());

        return studentMapper.entityToGetDTO(student);
    }

    @PostMapping(path = "supervisor")
    public SupervisorDTO loginAsSupervisor(@RequestBody AuthRequestDTO request){
        //TODO...
        Supervisor supervisor = supervisorService.authenticateForSupervisor(request.getUsername(), request.getPassword());

        return supervisorMapper.entityToGetDTO(supervisor);
    }

    @PostMapping(path = "administrator")
    public AdministratorDTO loginAsAdministrator(@RequestBody AuthRequestDTO request){
        //TODO...
        Administrator administrator = administratorService.authenticateForAdministrator(request.getUsername(), request.getPassword());

        return administratorMapper.entityToGetDTO(administrator);
    }

}
