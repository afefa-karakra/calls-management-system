package com.example.convoconvert.Repository;

import com.example.convoconvert.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterfaceRepository extends JpaRepository<User, String> {
}
