package com.vntana.core.rest.client.comment;

import com.vntana.core.model.comment.request.CreateProductCommentRequestModel;
import com.vntana.core.model.comment.request.DeleteProductCommentRequestModel;
import com.vntana.core.model.comment.request.FindProductCommentByFilterRequestModel;
import com.vntana.core.model.comment.request.UpdateProductCommentRequestModel;
import com.vntana.core.model.comment.response.CreateCommentResultResponse;
import com.vntana.core.model.comment.response.DeleteCommentResultResponse;
import com.vntana.core.model.comment.response.ProductCommentViewResultResponse;
import com.vntana.core.model.comment.response.UpdateCommentResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 9/8/20
 * Time: 5:07 PM
 */
@FeignClient(name = "coreProductComments", path = "product-comments", url = "${ms.core.url}")
public interface ProductCommentResourceClient {

    @PostMapping
    ResponseEntity<CreateCommentResultResponse> create(@RequestBody final CreateProductCommentRequestModel request);

    @PutMapping
    ResponseEntity<UpdateCommentResultResponse> update(@RequestBody final UpdateProductCommentRequestModel request);

    @DeleteMapping
    ResponseEntity<DeleteCommentResultResponse> delete(@RequestBody final DeleteProductCommentRequestModel request);

    @PostMapping("/search")
    ResponseEntity<ProductCommentViewResultResponse> search(@RequestBody final FindProductCommentByFilterRequestModel request);
}
