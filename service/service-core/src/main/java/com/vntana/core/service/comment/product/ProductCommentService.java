package com.vntana.core.service.comment.product;

import com.vntana.core.domain.comment.ProductComment;
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto;
import com.vntana.core.service.comment.product.dto.ProductCommentFindByProductUuidDto;
import com.vntana.core.service.comment.product.dto.ProductCommentUpdateDto;

import java.util.List;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 17:00
 */
public interface ProductCommentService {
 
    ProductComment create(final ProductCommentCreateDto dto);
    
    ProductComment update(final ProductCommentUpdateDto dto);

    ProductComment delete(final String uuid);
    
    ProductComment findByUuid(final String uuid);
    
    boolean existsByUuid(final String uuid);
    
    List<ProductComment> findByProductUuid(final ProductCommentFindByProductUuidDto dto);
}
