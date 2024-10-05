package com.abc1236.ms.service.cms.impl;

import cn.hutool.core.util.StrUtil;

import com.abc1236.ms.entity.cms.Banner;
import com.abc1236.ms.enumeration.cms.BannerTypeEnum;
import com.abc1236.ms.mapper.cms.BannerMapper;
import com.abc1236.ms.query.BannerQuery;
import com.abc1236.ms.service.cms.BannerService;
import com.abc1236.ms.vo.offcialsite.BannerVO;
import com.baomidou.mybatisplus.extension.toolkit.Db;
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
            return Db.save(banner);
        } else {
            return Db.updateById(banner);
        }
    }

    @Override
    public boolean remove(Long id) {
        return SqlHelper.retBool(bannerMapper.deleteById(id));
    }

    @Override
    public List<Banner> queryAllLike(String title) {
        return Db.lambdaQuery(Banner.class)
            .like(StrUtil.isNotBlank(title), Banner::getTitle, title)
            .list();
    }

    @Override
    public BannerVO queryBanner(String value) {
        BannerVO banner = new BannerVO();
        List<Banner> bannerList = Db.lambdaQuery(Banner.class)
            .eq(Banner::getType, value)
            .list();
        banner.setIndex(0);
        banner.setList(bannerList);
        return banner;
    }

    @Override
    public BannerVO queryIndexBanner() {
        return queryBanner(BannerTypeEnum.INDEX.getValue());
    }
}
