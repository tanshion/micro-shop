package com.abc1236.ms.service.cms;

import com.abc1236.ms.entity.cms.Banner;
import com.abc1236.ms.query.BannerQuery;
import com.abc1236.ms.vo.offcialsite.BannerVO;

import java.util.List;

public interface BannerService {
    boolean save(BannerQuery bannerQuery);

    boolean remove(Long id);

    List<Banner> queryAllLike(String title);

    BannerVO queryBanner(String value);
}
