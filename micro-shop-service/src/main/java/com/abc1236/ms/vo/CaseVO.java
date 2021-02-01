package com.abc1236.ms.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseVO {
    private BannerVO banner;
    private List<Product> caseList = new ArrayList<>();
}
