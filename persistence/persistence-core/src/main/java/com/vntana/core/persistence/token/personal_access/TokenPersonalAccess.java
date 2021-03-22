package com.vntana.core.persistence.token.personal_access;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Diana Gevorgyan
 * Date: 3/22/21
 * Time: 5:55 PM
 */
@Repository
public interface TokenPersonalAccess extends CrudRepository<TokenPersonalAccess, Long> {
}
