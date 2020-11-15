package com.abc1236.ms.bo;

import com.abc1236.ms.entity.system.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuBO extends Menu {
    private Long parentId;
}
