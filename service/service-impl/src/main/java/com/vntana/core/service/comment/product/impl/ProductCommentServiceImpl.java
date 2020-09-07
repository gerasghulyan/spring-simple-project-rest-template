package com.vntana.core.service.comment.product.impl;

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

import java.time.LocalDateTime;

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

    @Override
    public ProductComment create(final ProductCommentCreateDto dto) {
        Assert.notNull(dto, "The ProductCommentCreateDto should not be null");
        LOGGER.debug("Creating user {} comment for product - {}", dto.getUserUuid(), dto.getProductUuid());
        final User user = userService.getByUuid(dto.getUserUuid());
        final ProductComment productComment = new ProductComment(user, dto.getMessage(), dto.getProductUuid());
        final ProductComment savedProductComment = productCommentRepository.save(productComment);
        LOGGER.debug("Successfully creating user {} comment for product - {}", dto.getUserUuid(), dto.getProductUuid());
        return savedProductComment;
    }

    @Override
    public ProductComment update(final ProductCommentUpdateDto dto) {
        Assert.notNull(dto, "The ProductCommentUpdateDto should not be null");
        Assert.hasText(dto.getUuid(), "The ProductCommentUpdateDto uuid should not be null or empty");
        Assert.notNull(dto.getMessage(), "The ProductCommentUpdateDto message should not be null");
        LOGGER.debug("Updating product comment having uuid - {}", dto.getUuid());
        ProductComment productComment = (ProductComment) commentService.findByUuid(dto.getUuid());

        final ProductComment savedProductComment = productCommentRepository.save(productComment);
        LOGGER.debug("Successfully updated product comment having uuid - {}", dto.getUuid());
        return savedProductComment;
    }

    @Transactional
    @Override
    public ProductComment delete(final String uuid) {
        LOGGER.debug("Deleting product comment having uuid - {}", uuid);
        Assert.hasText(uuid, "The product comment uuid should not be null");
        ProductComment productComment = (ProductComment) commentService.findByUuid(uuid);
        productComment.setRemoved(LocalDateTime.now());

        final ProductComment deletedProductComment = productCommentRepository.save(productComment);
        LOGGER.debug("Successfully deleted product comment having uuid - {}", uuid);
        return deletedProductComment;
    }

    @Override
    public Page<ProductComment> findByProductUuid(final ProductCommentFindByProductUuidDto dto) {
        Assert.notNull(dto, "The ProductCommentFindByProductUuidDto should not be null");
        LOGGER.debug("Retrieving product comments by productUuid - {}", dto.getProductUuid());
        Page<ProductComment> productComments = productCommentRepository.findByProductUuidAndRemovedIsNull(dto.getProductUuid(), PageRequest.of(dto.getPage(), dto.getSize()));
        LOGGER.debug("Successfully retrieved product comments by productUuid - {}", dto.getProductUuid());
        return productComments;
    }
}