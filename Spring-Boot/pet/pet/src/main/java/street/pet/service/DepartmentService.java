package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Department;
import street.pet.domain.Vet;
import street.pet.repository.DepartmentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public Long save(Department department){
        validateDuplicatedName(department);
        departmentRepository.save(department);
        return department.getId();
    }

    private void validateDuplicatedName(Department department) {
        List<Department> findDepartment = departmentRepository.findByName(department.getName());
        if(!findDepartment.isEmpty()){
            throw new IllegalStateException("중복 이름입니다.");
        }
    }

    public List<Department> findDepartments() {
        return departmentRepository.findAll();
    }

    public Department findOne(Long id){
        return departmentRepository.findOne(id);
    }
}
