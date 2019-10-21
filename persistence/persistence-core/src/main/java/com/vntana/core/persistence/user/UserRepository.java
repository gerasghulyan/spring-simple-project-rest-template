package com.vntana.core.persistence.user;

import com.vntana.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Arthur Asatryan.
 * Date: 10/3/19
 * Time: 6:27 PM
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);
}
