package com.vntana.core.persistence.comment.user.product;

import com.vntana.core.domain.comment.ProductComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 17:33
 */
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {

    Optional<ProductComment> findByUuid(final String uuid);

    boolean existsByUuid(final String uuid);
    
    List<ProductComment> findByProductUuidAndRemovedIsNull(final String productUuid);
}