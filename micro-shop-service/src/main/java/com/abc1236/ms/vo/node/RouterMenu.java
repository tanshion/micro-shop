package com.abc1236.ms.vo.node;

import com.abc1236.ms.bo.MenuBO;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：enilu
 * @date ：Created in 2019/10/30 22:00
 */
@Data
@NoArgsConstructor
public class RouterMenu {
    private Long id;
    private Long parentId;
    private String path;
    private String component;
    private String name;
    private Integer num;
    private Boolean hidden = false;
    private MenuMeta meta;
    private List<RouterMenu> children = Lists.newArrayList();

    public RouterMenu(MenuBO source) {
        this.setPath(source.getUrl());
        this.setName(source.getName());
        MenuMeta meta = new MenuMeta();
        meta.setIcon(source.getIcon());
        //如果使用前端vue-i18n对菜单进行国际化，则title設置为code，且code需要与国际化资源文件中的key值一致
        meta.setTitle(source.getCode());
        //如果不需要做国际化，则title直接设置后台管理配置的菜单标题即可
        //meta.setTitle(String.valueOf(source[3]));
        this.setNum(source.getNum());
        this.setParentId(source.getParentId());
        this.setComponent(source.getComponent());
        this.setId(source.getId());
        this.setMeta(meta);
        this.setHidden(source.getHidden());
    }
}
