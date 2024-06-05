package com.example.convoconvert.Repository;

import com.example.convoconvert.DTO.CallsDTO;
import com.example.convoconvert.Entity.Calls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CallsInterfaceRepository extends JpaRepository<Calls, Long> {
    @Query("SELECT c FROM Calls c WHERE c.started = true")
    List<Calls> getStartedCalls();

    @Query("SELECT c FROM Calls c WHERE c.status = 'solved' ")
    List<Calls> getSolveCalls();

    @Query("SELECT c FROM Calls c WHERE c.status = 'notsolved' ")
    List<Calls> getNotSolveCalls();

    @Query("SELECT e.id, c.audio, c.audioText, c.date, c.time, c.started, c.status, c.keywords, c.nerTags, c.entityClasses, c.trash " +
            "FROM Calls c " +
            "JOIN Employee e ON c.id = e.id " +
            "WHERE e.id = :employeeId AND c.date = :callDate")
    List<Calls> getListOfCallsFilter(@Param("employeeId") Long employeeId, @Param("callDate") Date callDate);



    @Query("SELECT c.keywords FROM Calls c WHERE c.id = :id")
    List<String> getKeywordsByCallId(long id);

    @Query("SELECT c.nerTags FROM Calls c WHERE c.id = :id")
    List<String> getnerTagsByCallId(long id);

    @Query("SELECT c.entityClasses FROM Calls c WHERE c.id = :id")
    List<String> getEntityClassesByCallId(long id);

    @Query("SELECT c.date FROM Calls c WHERE c.id = :id")
    List<String> getDateByCallId(long id);

//    @Query("SELECT " +
//            "    cu.id AS customer_idfk, " +
//            "    e.name AS name, " +
//            "    c.audioText, " +
//            "    c.audio, " +
//            "    c.started " +
//            "FROM Calls c " +
//            "JOIN Customer cu ON c.customer.id = cu.id " +
//            "JOIN Employee e ON c.employee.name = e.name")
//    List<String> geDataWithEmployeeNameAndCustomerId();

    @Query("SELECT " +
            "    cu.id AS customerId, " +
            "    cu.name AS customerName, " +
            "    e.name AS employeeName, " +
            "    c.audioText, " +
            "    c.audio, " +
            "    c.nerTags,"+
            "    c.entityClasses,"+
            "    c.keywords,"+
            "    c.started " +
            "FROM Calls c " +
            "JOIN c.customer cu " +
            "JOIN c.employee e")
    List<Object []> geDataWithEmployeeNameAndCustomerId();

}
