package com.abc1236.ms.service.cms;

import com.abc1236.ms.entity.cms.Banner;
import com.abc1236.ms.query.BannerQuery;

import java.util.List;

public interface BannerService {
    boolean save(BannerQuery bannerQuery);

    boolean remove(Long id);

    List<Banner> queryAllLike(String title);
}
