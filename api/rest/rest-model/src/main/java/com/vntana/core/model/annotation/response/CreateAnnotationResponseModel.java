package com.vntana.core.model.annotation.response;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Vardan Aivazian
 * Date: 15.09.2020
 * Time: 17:59
 */
public class CreateAnnotationResponseModel extends AbstractUuidAwareResponseModel {

    public CreateAnnotationResponseModel() {
        super();
    }

    public CreateAnnotationResponseModel(final String uuid) {
        super(uuid);
    }
}