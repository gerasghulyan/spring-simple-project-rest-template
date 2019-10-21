package com.vntana.core.persistence.token;

import com.vntana.core.domain.token.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 12:54 PM
 */
@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
}
