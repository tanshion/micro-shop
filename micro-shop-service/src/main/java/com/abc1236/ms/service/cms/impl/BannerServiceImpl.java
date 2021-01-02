package com.abc1236.ms.service.cms.impl;

import cn.hutool.core.util.StrUtil;
import com.abc1236.ms.config.mybatis.SqlWrapper;
import com.abc1236.ms.entity.cms.Banner;
import com.abc1236.ms.mapper.cms.BannerMapper;
import com.abc1236.ms.query.BannerQuery;
import com.abc1236.ms.service.cms.BannerService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {
    private final BannerMapper bannerMapper;

    @Override
    public boolean save(BannerQuery bannerQuery) {
        Banner banner = BeanCopyUtils.copyBean(bannerQuery, Banner.class);
        if (banner.getId() == null) {
            return SqlHelper.retBool(bannerMapper.insert(banner));
        } else {
            return SqlHelper.retBool(bannerMapper.updateById(banner));
        }
    }

    @Override
    public boolean remove(Long id) {
        return SqlHelper.retBool(bannerMapper.deleteById(id));
    }

    @Override
    public List<Banner> queryAllLike(String title) {
        return SqlWrapper.query(bannerMapper)
            .like(StrUtil.isNotBlank(title), Banner::getTitle, title)
            .list();
    }
}
