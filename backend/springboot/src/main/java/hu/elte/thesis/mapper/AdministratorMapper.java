package hu.elte.thesis.mapper;

import hu.elte.thesis.dto.request.AdministratorPostDTO;
import hu.elte.thesis.dto.response.AdministratorDTO;
import hu.elte.thesis.model.Administrator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = MultipartFileMapper.class)
public abstract class AdministratorMapper {

    @Autowired
    private MultipartFileMapper multipartFileMapper;

    @Mapping(target = "id", source = "administrator.id")
    public abstract AdministratorDTO entityToGetDTO(Administrator administrator);

    public abstract List<AdministratorDTO> entityToGetDTO(List<Administrator> administrators);

    public Administrator postDTOToEntity(AdministratorPostDTO administratorPostDTO){

        if (administratorPostDTO == null){
            return null;
        }

        Administrator administrator = new Administrator();
        administrator.setName(administratorPostDTO.getName());
        administrator.setEmail(administratorPostDTO.getEmail());

        return administrator;
    }

}
