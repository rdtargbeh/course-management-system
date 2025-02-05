package backend.school_management_system.service.implement;

import backend.school_management_system.dto.InstructorDTO;
import backend.school_management_system.entity.Course;
import backend.school_management_system.entity.Instructor;
import backend.school_management_system.entity.User;
import backend.school_management_system.mapper.InstructorMapper;
import backend.school_management_system.repository.InstructorRepository;
import backend.school_management_system.service.CourseService;
import backend.school_management_system.service.InstructorService;
import backend.school_management_system.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstructorServiceImplementation implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;
    private final UserService userService;
    private final CourseService courseService;

    public InstructorServiceImplementation(InstructorRepository instructorRepository, InstructorMapper instructorMapper, UserService userService, CourseService courseService) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(() ->
                new EntityNotFoundException("Instructor with ID" + instructorId + " not found"));
    }

    @Override
    public Instructor findInstructorById(Long instructorId) {
        return instructorRepository.findInstructorById(instructorId);
    }

    @Override
    public Page<InstructorDTO> findInstructorsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Instructor> instructorsPage = instructorRepository.findInstructorsByName(name, pageRequest);
        return new PageImpl<>(instructorsPage.getContent().stream().map(instructor -> instructorMapper.fromInstructor(instructor))
                .collect(Collectors.toList()), pageRequest, instructorsPage.getTotalElements());
    }

    @Override
    public InstructorDTO loadInstructorByEmail(String email) {
        return instructorMapper.fromInstructor(instructorRepository.findInstructorByEmail(email));
    }

    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        User user = userService.createUser(instructorDTO.getUser().getEmail(),instructorDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(),"Instructor");
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(user);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public InstructorDTO updateInstructor(InstructorDTO instructorDTO) {
        Instructor loadedInstructor = loadInstructorById(instructorDTO.getInstructorId());
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(loadedInstructor.getUser());
        instructor.setCourses(loadedInstructor.getCourses());
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return instructorMapper.fromInstructor(updatedInstructor);
    }

    @Override
    public List<InstructorDTO> fetchInstructors() {
        return instructorRepository.findAll().stream().map(instructor -> instructorMapper.fromInstructor(instructor))
                .collect(Collectors.toList());
    }

    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for(Course course : instructor.getCourses()) {  // find and remove instructor courses
            courseService.removeCourse(course.getCourseId());
        }
        instructorRepository.deleteById(instructorId);
    }

}
