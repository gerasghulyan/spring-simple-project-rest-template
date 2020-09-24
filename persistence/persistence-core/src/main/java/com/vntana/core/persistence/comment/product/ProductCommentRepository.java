package com.vntana.core.persistence.comment.product;

import com.vntana.core.domain.comment.ProductComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 17:33
 */
public interface ProductCommentRepository extends JpaRepository<ProductComment, Long> {

    Page<ProductComment> findByProductUuidAndRemovedIsNullOrderByCreatedDesc(final String productUuid, final Pageable pageable);
}