package com.henge.controller;

import com.henge.model.Menu;
import com.henge.model.Resource;
import com.henge.model.RestResp;
import com.henge.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public List<Menu> menus() {
        return resourceService.menus();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource getById(@PathVariable("id") Long id) {
        return resourceService.get(id);
    }

    @RequestMapping(value = "/allMenus", method = RequestMethod.POST)
    public List<Menu> allMenus() {
        return resourceService.allMenus();
    }

    @RequestMapping(method = RequestMethod.POST)
    public RestResp add(@RequestBody Resource resource) {
        resourceService.add(resource);
        return RestResp.ok("添加成功");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RestResp edit(@RequestBody Resource resource) {
        resourceService.edit(resource);
        return RestResp.ok("编辑成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResp delete(@PathVariable("id") Long id) {
        resourceService.delete(id);
        return RestResp.ok("删除成功");
    }

    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public List<Resource> treeList() {
        return resourceService.treeList();
    }
}
