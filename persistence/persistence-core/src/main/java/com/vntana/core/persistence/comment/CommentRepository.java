package com.vntana.core.persistence.comment;

import com.vntana.core.domain.comment.AbstractComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 17:42
 */
public interface CommentRepository  extends JpaRepository<AbstractComment, Long> {

    Optional<AbstractComment> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);
}
