package com.abc1236.ms.vo;

import com.abc1236.ms.vo.node.Node;
import lombok.Data;

import java.util.List;

@Data
public class MenuTreeVO {
    List<Long> checkedIds;
    List<Node> treeData;
}
