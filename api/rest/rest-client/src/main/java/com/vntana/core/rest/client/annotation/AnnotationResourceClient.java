package com.vntana.core.rest.client.annotation;

import com.vntana.core.model.annotation.request.CreateAnnotationRequestModel;
import com.vntana.core.model.annotation.request.DeleteAnnotationRequestModel;
import com.vntana.core.model.annotation.request.FindAnnotationByFilterRequestModel;
import com.vntana.core.model.annotation.request.UpdateAnnotationRequestModel;
import com.vntana.core.model.annotation.response.AnnotationViewResultResponse;
import com.vntana.core.model.annotation.response.CreateAnnotationResultResponse;
import com.vntana.core.model.annotation.response.DeleteAnnotationResultResponse;
import com.vntana.core.model.annotation.response.UpdateAnnotationResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 17:59
 */
@FeignClient(name = "coreAnnotations", path = "annotations", url = "${ms.core.url}")
public interface AnnotationResourceClient {
    
    @PostMapping
    ResponseEntity<CreateAnnotationResultResponse> create(@RequestBody final CreateAnnotationRequestModel request);

    @PutMapping
    ResponseEntity<UpdateAnnotationResultResponse> update(@RequestBody final UpdateAnnotationRequestModel request);

    @DeleteMapping
    ResponseEntity<DeleteAnnotationResultResponse> delete(@RequestBody final DeleteAnnotationRequestModel request);

    @PostMapping("/search")
    ResponseEntity<AnnotationViewResultResponse> search(@RequestBody final FindAnnotationByFilterRequestModel request);
}
