package com.abc1236.ms.service.system;

import com.abc1236.ms.entity.system.FileInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface FileService {
    Page<FileInfo> queryPageByLike(Long page, Long limit, String originalFileName);
}
