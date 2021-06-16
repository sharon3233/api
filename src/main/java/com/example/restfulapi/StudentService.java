package com.example.restfulapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {

        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Student already exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("Student with id not found");
        }
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public void updateStudent(Long studentId, String name, String email, LocalDate dob) {
        Student studentToUpdate = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student not found"));
        if(name != null && name.length() > 0 && Objects.equals(studentToUpdate.getName(), name)) {
            studentToUpdate.setName(name);
            System.out.println("Name did update");
        }
        if(email != null && !Objects.equals(studentToUpdate.getEmail(), email)) {
      Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
        throw new IllegalStateException("Email in use");

        }
        studentToUpdate.setEmail(email);

    }
}
