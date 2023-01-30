package com.abc1236.ms.controller.system;


import com.abc1236.ms.constant.Permission;
import com.abc1236.ms.core.result.ResultEntity;
import com.abc1236.ms.entity.system.Cfg;
import com.abc1236.ms.mapper.system.CfgMapper;
import com.abc1236.ms.service.system.CfgService;
import com.abc1236.ms.service.system.FileService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanshion
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cfg")
public class CfgController {
    private final CfgService cfgService;
    private final CfgMapper cfgMapper;

    @Autowired
    private FileService fileService;

    /**
     * 查询参数列表
     */
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('" + Permission.CFG + "')")
    public ResultEntity<Page<Cfg>> list(Long page, Long limit, @RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
        Page<Cfg> entity = cfgService.queryPage(new Page<>(page, limit), cfgName, cfgValue);
        return ResultEntity.success(entity);
    }
    //
    ///**
    // * 分组查询参数
    // * @param cfgGroup
    // * @return
    // */
    //@RequestMapping(value = "/getByPrefix",method = RequestMethod.GET)
    //@RequiresPermissions(value = {Permission.CFG})
    //public Object list(@RequestParam(required = false) String cfgGroup) {
    //    List<Cfg> list = cfgService.queryAll(SearchFilter.build("cfgName", SearchFilter.Operator.LIKEL,cfgGroup));
    //    Map map = Maps.newHashMap();
    //    for(Cfg cfg:list){
    //        map.put(cfg.getCfgName(),cfg.getCfgValue());
    //    }
    //    return Rets.success(Maps.newHashMap("list",list,"map",map));
    //}
    //
    ///**
    // * 导出参数列表
    // * @param cfgName
    // * @param cfgValue
    // * @return
    // */
    //@RequestMapping(value = "/export",method = RequestMethod.GET)
    //@RequiresPermissions(value = {Permission.CFG})
    //public Object export(@RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
    //    Page<Cfg> page = new PageFactory<Cfg>().defaultPage();
    //    if(StringUtil.isNotEmpty(cfgName)){
    //        page.addFilter(SearchFilter.build("cfgName", SearchFilter.Operator.LIKE, cfgName));
    //    }
    //    if(StringUtil.isNotEmpty(cfgValue)){
    //        page.addFilter(SearchFilter.build("cfgValue", SearchFilter.Operator.LIKE, cfgValue));
    //    }
    //    page = cfgService.queryPage(page);
    //    FileInfo fileInfo = fileService.createExcel("templates/config.xlsx","系统参数.xlsx",Maps.newHashMap("list",page.getRecords()));
    //    return Rets.success(fileInfo);
    //}
    //@RequestMapping(method = RequestMethod.POST)
    //@BussinessLog(value = "编辑参数", key = "cfgName")
    //@RequiresPermissions(value = {Permission.CFG_EDIT})
    //public Object save(@ModelAttribute @Valid Cfg cfg){
    //    if(cfg.getId()!=null){
    //        Cfg old = cfgService.get(cfg.getId());
    //        LogObjectHolder.me().set(old);
    //        old.setCfgName(cfg.getCfgName());
    //        old.setCfgValue(cfg.getCfgValue());
    //        old.setCfgDesc(cfg.getCfgDesc());
    //        cfgService.saveOrUpdate(old);
    //    }else {
    //        cfgService.saveOrUpdate(cfg);
    //    }
    //    return Rets.success();
    //}
    //@RequestMapping(method = RequestMethod.DELETE)
    //@BussinessLog(value = "删除参数", key = "id")
    //@RequiresPermissions(value = {Permission.CFG_DEL})
    //public Object remove(@RequestParam Long id){
    //    logger.info("id:{}",id);
    //    if (id == null) {
    //        throw new ApplicationException(ApplicationExceptionEnum.REQUEST_NULL);
    //    }
    //    cfgService.deleteById(id);
    //    return Rets.success();
    //}
    //
    //@RequestMapping(value="saveGroup",method = RequestMethod.POST)
    //@BussinessLog(value = "编辑参数")
    //@RequiresPermissions(value = {Permission.CFG_EDIT})
    //public Object saveGroup(String json){
    //
    //    Map<String,String> map = Json.fromJson(Map.class,json);
    //    for(Map.Entry<String,String> entry:map.entrySet()){
    //        cfgService.update(entry.getKey(),entry.getValue());
    //    }
    //    return Rets.success();
    //}
}
