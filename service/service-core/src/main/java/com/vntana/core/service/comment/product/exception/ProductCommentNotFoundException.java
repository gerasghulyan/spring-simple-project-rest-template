package com.vntana.core.service.comment.product.exception;

/**
 * Created by Vardan Aivazian
 * Date: 04.09.2020
 * Time: 11:57
 */
public class ProductCommentNotFoundException extends RuntimeException{

    private final String uuid;

    public ProductCommentNotFoundException(final String uuid) {
        super(String.format("ProductComment not found for uuid %s", uuid));
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
