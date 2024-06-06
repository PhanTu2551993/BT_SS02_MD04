package ra.bt.service;

import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import ra.bt.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findEmployeeById(Integer empId);
    Employee save(Employee employee);
    Employee update(Employee employee);
    void delete(Integer empId);
    Page<Employee> getEmployeePaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction);
    Page<Employee> findEmployeesBySalaryRange(
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary,
            Integer page, Integer size);

    List<Employee> getEmployeesByFullNameWithPagingAndSortingDescendingBySalary(String searchName, Integer page, Integer itemPage);
    List<Employee> getTopBySalary();
}
