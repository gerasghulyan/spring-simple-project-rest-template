package com.vntana.core.rest.facade.user.component;

import com.vntana.core.rest.facade.user.component.dto.SendUserMentionDto;

/**
 * Created by Vardan Aivazian
 * Date: 06.10.2020
 * Time: 16:44
 */
public interface UserMentionEmailSenderComponent {

    void sendMentionedUsersEmails(final SendUserMentionDto dto);
}
