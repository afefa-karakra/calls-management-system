package com.example.convoconvert.Repository;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.Entity.Calls;
import com.example.convoconvert.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CallsInterfaceRepository extends JpaRepository<Calls, Long> {
    @Query("SELECT c FROM Calls c WHERE c.started = true")
    List<Calls> getStartedCalls();

    @Query("SELECT c FROM Calls c WHERE c.status = 'solve' ")
    List<Calls> getSolveCalls();

    @Query("SELECT c FROM Calls c WHERE c.status = 'notsolve' ")
    List<Calls> getNotSolveCalls();

    @Query("SELECT e.id AS employeeId, c.date AS callDate FROM Employee e JOIN Calls c")
    List<Calls> getListOfCallsFillter (long id , Date date);

}
