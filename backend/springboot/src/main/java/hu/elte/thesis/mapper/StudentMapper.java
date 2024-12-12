package hu.elte.thesis.mapper;

import hu.elte.thesis.dto.request.StudentPostDTO;
import hu.elte.thesis.dto.response.StudentDTO;
import hu.elte.thesis.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = MultipartFileMapper.class)
public abstract class StudentMapper {

    @Autowired
    private MultipartFileMapper multipartFileMapper;

    @Mapping(target = "id", source = "student.id")
    public abstract StudentDTO entityToGetDTO(Student student);

    public abstract List<StudentDTO> entityToGetDTO(List<Student> students);

    public Student postDTOToEntity(StudentPostDTO studentPostDTO){

        if (studentPostDTO == null){
            return null;
        }

        Student student = new Student();
        student.setName(studentPostDTO.getName());
        student.setEmail(studentPostDTO.getEmail());

        return student;
    }

}
