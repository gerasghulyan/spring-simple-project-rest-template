package com.vntana.core.rest.client.comment;

import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel;
import com.vntana.core.model.comment.response.CreateCommentResultResponse;
import com.vntana.core.model.comment.response.ProductCommentViewResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 5:07 PM
 */
@FeignClient(name = "coreProductComments", path = "product-comments", url = "${ms.core.url}")
public interface ProductCommentResourceClient {

    @PostMapping
    ResponseEntity<CreateCommentResultResponse> create(@RequestBody final CreateProductCommentRequestModel request);

    @PostMapping("/search")
    ResponseEntity<ProductCommentViewResultResponse> search(@RequestBody final FindProductCommentByFilterRequestModel request);
}
