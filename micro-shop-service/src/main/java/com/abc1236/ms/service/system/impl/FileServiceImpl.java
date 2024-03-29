package com.abc1236.ms.service.system.impl;


import com.abc1236.ms.entity.system.FileInfo;
import com.abc1236.ms.mapper.system.FileInfoMapper;
import com.abc1236.ms.service.system.FileService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private final FileInfoMapper fileInfoMapper;

    @Override
    public Page<FileInfo> queryPageByLike(Long page, Long limit, String originalFileName) {
        return Db.lambdaQuery(FileInfo.class)
            .like(FileInfo::getOriginalFileName, "%" + originalFileName + "%")
            .page(new Page<>(page, limit));
    }
}
