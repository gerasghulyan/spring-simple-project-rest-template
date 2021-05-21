package com.vntana.core.service.whitelist.mediator;

import com.vntana.core.service.whitelist.mediator.dto.SaveWhitelistIpLifecycleDto;

/**
 * Created by Geras Ghulyan
 * Date: 18.12.19
 * Time: 10:25
 */
public interface WhitelistIpLifecycleMediator {

    void onSaved(final SaveWhitelistIpLifecycleDto dto);
}