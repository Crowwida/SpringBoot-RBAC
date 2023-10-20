package com.henge.controller;

import com.henge.model.RestResp;
import com.henge.model.Role;
import com.henge.service.RoleService;
import com.henge.vo.DataTable;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.POST)
    public RestResp add(@RequestBody Role role) {
        roleService.add(role);
        return RestResp.ok("添加成功！");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RestResp edit(@RequestBody Role role) {
        roleService.edit(role);
        return RestResp.ok("更新成功！");
    }

    @RequestMapping(value = "/tables", method = RequestMethod.POST)
    public DataTable<Role> tables(@RequestBody String tablePage) {
        return roleService.tables(new JsonObject(tablePage));
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<Role> tree() {
        return roleService.tree();
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public Role getById(@PathVariable("roleId") Long roleId) {
        return roleService.get(roleId);
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public RestResp delete(@PathVariable("roleId") Long roleId) {
        roleService.delete(roleId);
        return RestResp.ok("删除成功！");
    }

    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public RestResp grant(@RequestBody Role role) {
        roleService.grant(role);
        return RestResp.ok("授权成功！");
    }
}
