package com.example.convoconvert.Repository;

import com.example.convoconvert.Entity.Customer;
import com.example.convoconvert.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInterfaceRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.name = :name")
    Customer findByName(@Param("name") String name);

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    Customer findByPhoneNumber(@Param("phoneNumber") Integer phoneNumber);

    @Query("SELECT c.name FROM Customer c")
    List<String> findAllCustomerNames();
}
