package com.vntana.core.listener.whitelist;

/**
 * Created by Geras Ghulyan
 * Date: 18.12.19
 * Time: 17:57
 */
public interface WhitelistIpCacheUpdateEventListener {

    void handleEvent(final WhitelistIpLifecyclePayload payload);

}