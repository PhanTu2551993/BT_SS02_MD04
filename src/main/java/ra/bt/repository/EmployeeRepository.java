package ra.bt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.bt.model.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Integer>,JpaRepository<Employee, Integer> {
    @Query("select e from Employee e where e.fullName like concat('%',:fullName,'%')")
    Page<Employee> findEmployeesByFullNameAAndSort(@Param("fullName") String fullName, Pageable pageable);

    @Query("select e from Employee e where e.salary between :minSalary and :maxSalary")
    Page<Employee> findEmployeesBySalaryRange(
            @Param("minSalary") Double minSalary,
            @Param("maxSalary") Double maxSalary,
            Pageable pageable
    );

    @Query("select e from Employee e where e.fullName like concat('%',:name,'%') order by e.salary desc ")
    List<Employee> getEmployeesByFullNameWithPagingAndSortingDescendingBySalary(String name, Pageable pageable);


    @Query("select e from Employee e order by e.salary desc limit 10")
    List<Employee> getTopBySalary();
}
