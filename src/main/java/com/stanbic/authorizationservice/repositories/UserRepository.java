package com.stanbic.authorizationservice.repositories;

import com.stanbic.authorizationservice.entities.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bkariuki
 */
@Repository
public interface UserRepository extends JpaRepository<SystemUser, Long> {

    SystemUser findByEmail(String email);

    List<SystemUser> deleteAllById(Long id);
}
