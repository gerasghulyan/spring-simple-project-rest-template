package com.vntana.core.rest.facade.comment.builder.impl;

import com.vntana.core.domain.comment.ProductComment;
import com.vntana.core.model.comment.response.ProductCommentViewResponseModel;
import com.vntana.core.model.user.response.get.model.UserViewResponseModel;
import com.vntana.core.rest.facade.comment.builder.ProductCommentViewModelBuilder;
import com.vntana.core.rest.facade.user.builder.UserModelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 3:37 PM
 */
@Component
class ProductCommentViewModelBuilderImpl implements ProductCommentViewModelBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCommentViewModelBuilderImpl.class);

    private final CommentTaggedUsersFinderService commentTaggedUsersFinderService;
    private final UserModelBuilder userModelBuilder;

    public ProductCommentViewModelBuilderImpl(final CommentTaggedUsersFinderService commentTaggedUsersFinderService,
                                              final UserModelBuilder userModelBuilder) {
        this.commentTaggedUsersFinderService = commentTaggedUsersFinderService;
        this.userModelBuilder = userModelBuilder;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public ProductCommentViewResponseModel build(final ProductComment comment) {
        LOGGER.debug("Building product comment view model for the provided product comment - {}", comment);
        Assert.notNull(comment, "The product comment should not be null");
        final ProductCommentViewResponseModel model = new ProductCommentViewResponseModel(
                comment.getUuid(),
                comment.getProductUuid(),
                comment.getMessage(),
                userModelBuilder.build(comment.getUser()),
                buildTaggedUsersViewModels(comment),
                comment.getCreated(),
                comment.getUpdated()
        );
        LOGGER.debug(
                "Successfully built product comment view model for the provided product comment - {}, result - {}",
                comment,
                model
        );
        return model;
    }

    private List<UserViewResponseModel> buildTaggedUsersViewModels(final ProductComment comment) {
        return commentTaggedUsersFinderService.find(comment).stream()
                .map(userModelBuilder::build)
                .collect(Collectors.toList());
    }
}
