package com.vntana.core.persistence.token.resetpassword;

import com.vntana.core.domain.token.TokenResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Geras Ghulyan
 * Date: 6/23/20
 * Time: 12:02 PM
 */
@Repository
public interface TokenResetPasswordRepository extends JpaRepository<TokenResetPassword, Long> {
}
