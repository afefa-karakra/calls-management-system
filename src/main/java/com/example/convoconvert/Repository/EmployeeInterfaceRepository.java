package com.example.convoconvert.Repository;


import com.example.convoconvert.Entity.Customer;
import com.example.convoconvert.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeInterfaceRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.name = :name")
    Employee findByName(@Param("name") String name);
   /* @Query("SELECT e FROM Employee e WHERE e.name = :name")
    Optional<Employee> findByName(@Param("name") String name);
*/

}
