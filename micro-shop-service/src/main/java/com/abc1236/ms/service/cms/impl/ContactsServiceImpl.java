package com.abc1236.ms.service.cms.impl;

import cn.hutool.core.date.DateUtil;

import com.abc1236.ms.entity.cms.Contacts;
import com.abc1236.ms.mapper.cms.ContactsMapper;
import com.abc1236.ms.service.cms.ContactsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactsServiceImpl implements ContactsService {
    private final ContactsMapper contactsMapper;

    @Override
    public Page<Contacts> queryPage(Long page, Long limit, String userName, String mobile, String startDate, String endDate) {
        return Db.lambdaQuery(Contacts.class)
            .ge(Contacts::getCreateTime, DateUtil.parse(startDate, "yyyyMMddHHmmss"))
            .le(Contacts::getCreateTime, DateUtil.parse(endDate, "yyyyMMddHHmmss"))
            .eq(Contacts::getUserName, userName)
            .eq(Contacts::getMobile, mobile)
            .page(new Page<>(page, limit));
    }

    @Override
    public boolean insert(Contacts contacts) {
        return SqlHelper.retBool(contactsMapper.insert(contacts));
    }
}