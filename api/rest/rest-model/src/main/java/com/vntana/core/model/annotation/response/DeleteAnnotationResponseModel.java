package com.vntana.core.model.annotation.response;

import com.vntana.commons.api.model.response.impl.AbstractUuidAwareResponseModel;

/**
 * Created by Vardan Aivazian
 * Date: 16.09.2020
 * Time: 13:07
 */
public class DeleteAnnotationResponseModel extends AbstractUuidAwareResponseModel {

    public DeleteAnnotationResponseModel() {
        super();
    }

    public DeleteAnnotationResponseModel(final String uuid) {
        super(uuid);
    }
}
