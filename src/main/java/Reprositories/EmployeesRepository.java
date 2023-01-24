package Reprositories;

import Model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {


    boolean existsByIsCeoTrue();
    boolean findByManagerId(int id);
    List<Employees> findAllByIsManagerAndIsCeo(boolean isManager, boolean isCeo);

    List<Employees> findAllByIsCeo(boolean isCeo);

    boolean existsByIsCeoTrueAndIdNot(int id);

}
