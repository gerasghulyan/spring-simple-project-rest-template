package com.vntana.core.service.comment.product.impl;

import com.vntana.commons.service.exception.EntityNotFoundForUuidException;
import com.vntana.core.domain.comment.ProductComment;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.comment.product.ProductCommentRepository;
import com.vntana.core.service.comment.CommentService;
import com.vntana.core.service.comment.product.ProductCommentService;
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto;
import com.vntana.core.service.comment.product.dto.ProductCommentFindByProductUuidDto;
import com.vntana.core.service.comment.product.dto.ProductCommentUpdateDto;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 17:32
 */
@Service
class ProductCommentServiceImpl implements ProductCommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommentServiceImpl.class);

    private final ProductCommentRepository productCommentRepository;
    private final CommentService commentService;
    private final UserService userService;

    public ProductCommentServiceImpl(final ProductCommentRepository productCommentRepository, final UserService userService, final CommentService commentService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.productCommentRepository = productCommentRepository;
        this.userService = userService;
        this.commentService = commentService;
    }

    @Transactional
    @Override
    public ProductComment create(final ProductCommentCreateDto dto) {
        Assert.notNull(dto, "The ProductCommentCreateDto should not be null");
        LOGGER.debug("Creating product comment for the provided dto - {}", dto);
        final User user = userService.getByUuid(dto.getUserUuid());
        final ProductComment productComment = new ProductComment(user, dto.getMessage(), dto.getProductUuid());
        final ProductComment savedProductComment = productCommentRepository.save(productComment);
        LOGGER.debug("Successfully creating product comment for the provided dto - {}", dto);
        return savedProductComment;
    }

    @Transactional
    @Override
    public ProductComment update(final ProductCommentUpdateDto dto) {
        Assert.notNull(dto, "The ProductCommentUpdateDto should not be null");
        LOGGER.debug("Updating product comment for the provided dto - {}", dto);
        ProductComment productComment = (ProductComment) commentService.findByUuid(dto.getUuid())
                        .orElseThrow(() -> new EntityNotFoundForUuidException(dto.getUuid(), ProductComment.class));
        productComment.setMessage(dto.getMessage());
        final ProductComment savedProductComment = productCommentRepository.save(productComment);
        LOGGER.debug("Successfully updated product comment for the provided dto - {}", dto);
        return savedProductComment;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductComment> findByProductUuid(final ProductCommentFindByProductUuidDto dto) {
        Assert.notNull(dto, "The ProductCommentFindByProductUuidDto should not be null");
        LOGGER.debug("Retrieving product comment by the provided dto - {}", dto);
        Page<ProductComment> productComments = productCommentRepository.findByProductUuidAndRemovedIsNullOrderByCreated(dto.getProductUuid(), PageRequest.of(dto.getPage(), dto.getSize()));
        LOGGER.debug("Successfully product comment by the provided dto - {}", dto);
        return productComments;
    }
}