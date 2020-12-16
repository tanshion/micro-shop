package com.abc1236.ms.controller.shop;

import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.aop.BussinessLog;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.shop.Category;
import com.abc1236.ms.service.shop.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品类别")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shop/category")
public class CategoryController {
    private final CategoryService categoryService;
    //@Autowired
    //private CategoryBannerRelService categoryBannerRelService;
    //@Autowired
    //private AttrKeyService attrKeyService;
    //@Autowired
    //private GoodsService goodsService;


    @ApiOperation("商品类别列表分页")
    @GetMapping(value = "/list")
    public ResultEntity<Page<Category>> list(Long page, Long limit) {
        Page<Category> categoryPage = categoryService.queryPage(page, limit);
        return ResultEntity.success(categoryPage);
    }
    @ApiOperation("所有商品类别列表")
    @GetMapping(value = "/getAll")
    public ResultEntity<List<Category>> getAll() {
        List<Category> categories = categoryService.queryAll();
        return ResultEntity.success(categories);
    }

    @PostMapping
    @ApiOperation("编辑商品类别")
    @BussinessLog(value = "编辑商品类别", key = "name")
    @PreAuthorize("hasAuthority('" + Permission.CATEGORY_EDIT + "')")
    public ResultEntity<String> save(@ModelAttribute Category category) {
        if (category.getId() == null) {
            categoryService.insert(category);
        } else {
            categoryService.update(category);
        }
        return ResultEntity.success();
    }

    //@RequestMapping(method = RequestMethod.DELETE)
    //@BussinessLog(value = "删除商品类别", key = "id")
    //@RequiresPermissions(value = {Permission.CATEGORY_EDIT})
    //public Object remove(Long id) {
    //    if (id == null) {
    //        throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
    //    }
    //    long goodsCount = goodsService.count(SearchFilter.build("idCategory", id));
    //    if (goodsCount > 0) {
    //        throw new ApplicationException(ApplicationExceptionEnum.DATA_CANNOT_REMOVE);
    //    }
    //    categoryService.deleteById(id);
    //    return Rets.success();
    //}
    //
    //@RequestMapping(value = "/getBanners/{idCategory}", method = RequestMethod.GET)
    //public Object getBanners(@PathVariable("idCategory") Long idCategory) {
    //    if (idCategory == null) {
    //        throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
    //    }
    //    List<CategoryBannerRel> relList = categoryBannerRelService.queryAll(SearchFilter.build("idCategory", SearchFilter.Operator.EQ, idCategory));
    //    List<Banner> bannerList = Lists.newArrayList();
    //    relList.forEach(item -> {
    //        bannerList.add(item.getBanner());
    //    });
    //
    //    return Rets.success(bannerList);
    //}
    //
    //@RequestMapping(value = "getAttrKeys/{idCategory}", method = RequestMethod.GET)
    //public Object getAttrKeys(@PathVariable("idCategory") Long idCategory) {
    //    if (idCategory == null) {
    //        throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
    //    }
    //    List<AttrKey> list = attrKeyService.queryBy(idCategory);
    //    return Rets.success(list);
    //
    //}
    //
    //@RequestMapping(value = "/removeBanner/{idCategory}/{idBanner}", method = RequestMethod.DELETE)
    //@RequiresPermissions(value = {Permission.CATEGORY_EDIT})
    //public Object removeBanner(@PathVariable("idCategory") Long idCategory,
    //    @PathVariable("idBanner") Long idBanner) {
    //    if (idCategory == null) {
    //        throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
    //    }
    //    CategoryBannerRel rel = categoryBannerRelService.get(Lists.newArrayList(
    //        SearchFilter.build("idCategory", idCategory),
    //        SearchFilter.build("idBanner", idBanner)
    //    ));
    //    if (rel != null) {
    //        categoryBannerRelService.delete(rel);
    //    }
    //    return Rets.success();
    //}
    //
    //@RequestMapping(value = "/setBanner/{idCategory}/{idBanner}", method = RequestMethod.POST)
    //@RequiresPermissions(value = {Permission.CATEGORY_EDIT})
    //public Object setBanner(@PathVariable("idCategory") Long idCategory,
    //    @PathVariable("idBanner") Long idBanner) {
    //    if (idCategory == null) {
    //        throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
    //    }
    //    CategoryBannerRel rel = categoryBannerRelService.get(Lists.newArrayList(
    //        SearchFilter.build("idCategory", idCategory),
    //        SearchFilter.build("idBanner", idBanner)
    //    ));
    //    if (rel != null) {
    //        return Rets.success();
    //    }
    //    rel = new CategoryBannerRel();
    //    rel.setIdCategory(idCategory);
    //    rel.setIdBanner(idBanner);
    //    categoryBannerRelService.insert(rel);
    //    return Rets.success();
    //}

}
