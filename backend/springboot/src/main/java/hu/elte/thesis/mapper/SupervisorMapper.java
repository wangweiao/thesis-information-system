package hu.elte.thesis.mapper;

import hu.elte.thesis.dto.request.SupervisorPostDTO;
import hu.elte.thesis.dto.response.SupervisorDTO;
import hu.elte.thesis.model.Supervisor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = MultipartFileMapper.class)
public abstract class SupervisorMapper {

    @Autowired
    private MultipartFileMapper multipartFileMapper;

    @Mapping(target = "id", source = "supervisor.id")
    public abstract SupervisorDTO entityToGetDTO(Supervisor supervisor);

    public abstract List<SupervisorDTO> entityToGetDTO(List<Supervisor> supervisors);

    public Supervisor postDTOToEntity(SupervisorPostDTO supervisorPostDTO){

        if (supervisorPostDTO == null){
            return null;
        }

        Supervisor supervisor = new Supervisor();
        supervisor.setName(supervisorPostDTO.getName());
        supervisor.setEmail(supervisorPostDTO.getEmail());

        return supervisor;
    }

}
