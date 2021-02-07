package com.abc1236.ms.vo;

import com.abc1236.ms.vo.offcialsite.BannerVO;
import com.abc1236.ms.vo.offcialsite.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseVO {
    private BannerVO banner;
    private List<Product> caseList = new ArrayList<>();
}
