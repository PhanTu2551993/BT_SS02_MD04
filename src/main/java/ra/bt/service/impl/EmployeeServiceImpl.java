package ra.bt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.bt.model.entity.Employee;
import ra.bt.repository.EmployeeRepository;
import ra.bt.service.EmployeeService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findEmployeeById(Integer empId) {
        return employeeRepository.findById(empId).orElseThrow(()->new NoSuchElementException("Khong ton tai"));
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
    employeeRepository.findById(employee.getEmpId());
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Integer empId) {
        employeeRepository.deleteById(empId);
    }

    @Override
    public Page<Employee> getEmployeePaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction) {
        Pageable pageable = null;
        if(orderBy!=null && !orderBy.isEmpty()){
            Sort sort = null;
            switch (direction){
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page, itemPage,sort);
        }else{
            pageable = PageRequest.of(page, itemPage);
        }
        if(searchName!=null && !searchName.isEmpty()){
            return employeeRepository.findEmployeesByFullNameAAndSort(searchName,pageable);
        }else{
            return employeeRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Employee> findEmployeesBySalaryRange(Double minSalary, Double maxSalary,Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findEmployeesBySalaryRange(minSalary, maxSalary,pageable);
    }

    @Override
    public List<Employee> getEmployeesByFullNameWithPagingAndSortingDescendingBySalary(String searchName, Integer page, Integer itemPage) {
        Pageable pageable = PageRequest.of(page, itemPage);
        if (searchName == null || searchName.isEmpty()) {
            return employeeRepository.findAll(pageable).getContent();
        } else {
            return employeeRepository.getEmployeesByFullNameWithPagingAndSortingDescendingBySalary(searchName, pageable);
        }
    }

    @Override
    public List<Employee> getTopBySalary() {
        return employeeRepository.getTopBySalary();
    }
}
