package com.vntana.core.service.comment.product.impl;

import com.vntana.core.domain.comment.ProductComment;
import com.vntana.core.domain.user.User;
import com.vntana.core.persistence.comment.user.product.ProductCommentRepository;
import com.vntana.core.service.comment.product.ProductCommentService;
import com.vntana.core.service.comment.product.dto.ProductCommentCreateDto;
import com.vntana.core.service.comment.product.dto.ProductCommentFindByProductUuidDto;
import com.vntana.core.service.comment.product.dto.ProductCommentUpdateDto;
import com.vntana.core.service.comment.product.exception.ProductCommentNotFoundException;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Vardan Aivazian
 * Date: 03.09.2020
 * Time: 17:32
 */
@Service
class ProductCommentServiceImpl implements ProductCommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommentServiceImpl.class);

    private final ProductCommentRepository productCommentRepository;
    private final UserService userService;

    public ProductCommentServiceImpl(final ProductCommentRepository productCommentRepository, final UserService userService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.productCommentRepository = productCommentRepository;
        this.userService = userService;
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
        LOGGER.debug("Updating user product comment having uuid - {}", dto.getUuid());
        final Optional<ProductComment> productCommentOptional = productCommentRepository.findByUuid(dto.getUuid());

        if (!productCommentOptional.isPresent()) {
            LOGGER.debug("Can not find ProductComment by uuid - {}", dto.getUuid());
            throw new ProductCommentNotFoundException(dto.getUuid());
        }

        final ProductComment productComment = productCommentOptional.get();
        productComment.setMessage(dto.getMessage());
        final ProductComment savedProductComment = productCommentRepository.save(productComment);
        LOGGER.debug("Successfully updated user product comment having uuid - {}", dto.getUuid());
        return productComment;
    }

    @Transactional
    @Override
    public ProductComment delete(final String uuid) {
        LOGGER.debug("Deleting user product comment having uuid - {}", uuid);
        Assert.hasText(uuid, "The user product comment uuid should not be null");
        final ProductComment productComment = findByUuid(uuid);
        productComment.setRemoved(LocalDateTime.now());
        final ProductComment deletedProductComment = productCommentRepository.save(productComment);
        LOGGER.debug("Successfully deleted user product comment having uuid - {}", uuid);
        return deletedProductComment;
    }

    @Transactional(readOnly = true)
    @Override
    public ProductComment findByUuid(final String uuid) {
        Assert.hasText(uuid, "The comment uuid should not be null or empty");
        LOGGER.debug("Retrieving user product comments by uuid - {}", uuid);
        final ProductComment productComment = productCommentRepository.findByUuid(uuid)
                .orElseThrow(() -> new ProductCommentNotFoundException(uuid));
        LOGGER.debug("Successfully retrieved user product comments by uuid - {}", uuid);
        return productComment;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByUuid(final String uuid) {
        Assert.hasText(uuid, "The uuid should not be null or empty");
        LOGGER.debug("Checking existence of user product comment by uuid - {}", uuid);
        final boolean existence = productCommentRepository.existsByUuid(uuid);
        LOGGER.debug("Successfully checked existence of user product comment by uuid - {}", uuid);
        return existence;
    }

    @Override
    public List<ProductComment> findByProductUuid(final ProductCommentFindByProductUuidDto dto) {
        Assert.notNull(dto, "The ProductCommentFindByProductUuidDto should not be null");
        LOGGER.debug("Retrieving user product comments by productUuid - {}", dto.getProductUuid());
        final List<ProductComment> productComments = productCommentRepository.findByProductUuidAndRemovedIsNull(dto.getProductUuid());
        LOGGER.debug("Successfully retrieved user product comments by productUuid - {}", dto.getProductUuid());
        return productComments;
    }
}