package ra.bt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.bt.model.dto.request.EmployeeRequest;
import ra.bt.model.entity.Employee;
import ra.bt.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer empId){
        return new ResponseEntity<>(employeeService.findEmployeeById(empId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> insertEmployee(@RequestBody Employee emp){
        return new ResponseEntity<>(employeeService.save(emp),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee emp){
        return new ResponseEntity<>(employeeService.update(emp),HttpStatus.OK);
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer empId){
        employeeService.delete(empId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/searchAndPaging")
    public ResponseEntity<List<Employee>> getEmployeeWithSearchAndPage(@RequestBody EmployeeRequest employeeRequest){
        List<Employee> content = employeeService.getEmployeePaging(employeeRequest.getName(), employeeRequest.getPage() - 1, employeeRequest.getItemPage(), employeeRequest.getSortBy(), employeeRequest.getDirection()).getContent();
        return new ResponseEntity<>(content,HttpStatus.OK);
    }

    @GetMapping("/searchSalaryRange")
    public ResponseEntity<List<Employee>> getEmployeeWithSearchSalaryRange(@RequestParam("minSalary") Double minSalary,
                                                                           @RequestParam("maxSalary") Double maxSalary,
                                                                           @RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Employee> employeePage = employeeService.findEmployeesBySalaryRange(minSalary, maxSalary, page, size);
        List<Employee> employees = employeePage.getContent();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/searchByNameSalaryDesc")
    public ResponseEntity<List<Employee>> searchEmployeeByNameWithPagingAndSalaryDesc(@RequestBody EmployeeRequest employeeRequest){
        List<Employee> list = employeeService.getEmployeesByFullNameWithPagingAndSortingDescendingBySalary(employeeRequest.getName(),employeeRequest.getPage()-1,employeeRequest.getItemPage());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("/getTopTenSalary")
    public ResponseEntity<List<Employee>> getTopTenSalary(){
        return new ResponseEntity<>(employeeService.getTopBySalary(),HttpStatus.OK);
    }

}
