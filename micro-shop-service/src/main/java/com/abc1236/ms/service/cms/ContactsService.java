package com.abc1236.ms.service.cms;

import com.abc1236.ms.entity.cms.Contacts;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ContactsService {
    Page<Contacts> queryPage(Long page, Long limit, String userName, String mobile, String startDate, String endDate);
}