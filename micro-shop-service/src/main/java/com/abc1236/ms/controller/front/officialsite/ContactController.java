package com.abc1236.ms.controller.front.officialsite;

import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.cms.Contacts;
import com.abc1236.ms.service.cms.ContactsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "offcialsite")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/offcialsite/contact")
public class ContactController {
    private final ContactsService contactsService;

    @RequestMapping(method = RequestMethod.POST)
    public ResultEntity<Object> save(@Valid Contacts contacts) {
        contactsService.insert(contacts);
        return ResultEntity.success();
    }
}
