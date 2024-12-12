package hu.elte.thesis.service;

import hu.elte.thesis.model.Student;
import hu.elte.thesis.repository.StudentRepository;
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
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Cacheable(value = "students", key = "#id")
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id); // Example user data
    }

    // Save a student and update cache with the new student data
    @CachePut(value = "students", key = "#student.id")
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Evict the cache entry when a student is deleted
    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(Long id) {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Student with id " + id + " does not exist!");
        }
        studentRepository.deleteById(id);
    }

    // Evict all cached entries in "students" when a bulk operation is done
    @CacheEvict(value = "students", allEntries = true)
    public void clearCache() {
        // Some bulk operation or cache clear logic
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByUsername(student.getUsername());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken!");
        }
        studentRepository.save(student);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exist!"));

        if (name != null &&
                !name.isEmpty() &&
                !name.equals(student.getName())) {
            student.setName(name);
        }

        if (email != null &&
                !email.isEmpty() &&
                !email.equals(student.getEmail())) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email taken!");
            }
            student.setEmail(email);
        }
    }

    public Student authenticateForStudent(String username , String password){
        Student student = studentRepository.findStudentByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with username '%s' does not exist.", username)));

//        if (!student.isActive()){
//            throw new IllegalStateException("driver is not active");
//        }

        if (!student.getPassword().equals(password)){
            throw new IllegalArgumentException("The provided username or password does not match.");
        }

        return student;
    }

}
