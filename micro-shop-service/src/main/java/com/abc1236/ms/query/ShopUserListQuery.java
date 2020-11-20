package com.abc1236.ms.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ShopUserListQuery {
    Long page;
    Long limit;
    String mobile;
    String nickName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startRegDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endRegDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startLastLoginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date endLastLoginTime;


}
