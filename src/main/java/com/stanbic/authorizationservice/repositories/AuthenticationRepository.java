package com.stanbic.authorizationservice.repositories;

import com.stanbic.authorizationservice.entities.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bkariuki
 */
@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {

    Authentication findByUsername(String username);

    Authentication findByUserId(Long userId);

    List<Authentication> deleteAllByUserId(Long userId);
}
