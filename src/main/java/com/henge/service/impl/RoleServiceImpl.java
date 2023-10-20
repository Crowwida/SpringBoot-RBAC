package com.henge.service.impl;

import com.henge.mapper.ResourceMapper;
import com.henge.mapper.RoleMapper;
import com.henge.mapper.UserMapper;
import com.henge.model.Role;
import com.henge.service.RoleService;
import com.henge.util.SessionUtil;
import com.henge.vo.DataTable;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static com.henge.util.AppConst.FIFTH_VALUE;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void add(Role role) {
        role.setId(UUID.randomUUID().getMostSignificantBits() & FIFTH_VALUE);
        roleMapper.save(role);

        // 刚刚添加的角色，赋予给当前的用户
        userMapper.saveRoles(SessionUtil.getCurrUid(), Collections.singletonList(role.getId()));
    }

    @Override
    public Role get(Long roleId) {
        Role role = roleMapper.getById(roleId);
        List<Long> resourceIds = resourceMapper.getRoleResourceIds(roleId);
        List<String> resourceNames = resourceMapper.getRoleResourceNames(roleId);

        role.setResourceIds(resourceIds);
        role.setResourceNames(resourceNames);
        return role;
    }

    @Override
    public void edit(Role role) {
        roleMapper.update(role);
    }

    @Override
    public List<Role> treeGrid() {
        Map<String, Object> params = new HashMap<>();
        Long currUid = SessionUtil.getCurrUid();
        if (currUid != null) {
            params.put("userId", currUid);// 查自己有权限的角色
        }

        List<Role> roles = roleMapper.getRoleList(params);
        roles.forEach(role -> {
            List<Long> resourceIds = resourceMapper.getRoleResourceIds(role.getId());
            List<String> resourceNames = resourceMapper.getRoleResourceNames(role.getId());

            role.setResourceIds(resourceIds);
            role.setResourceNames(resourceNames);
        });
        return roles;
    }

    @Override
    public void delete(Long id) {
        // PID的外键为级联删除
        roleMapper.deleteById(id);
    }

    @Override
    public List<Role> roles() {
        Map<String, Object> params = new HashMap<>();
        Long currUid = SessionUtil.getCurrUid();
        if (currUid != null) {
            params.put("userId", currUid);// 查自己有权限的角色
        }
        return roleMapper.getRoleList(params);
    }

    @Override
    public List<Role> allRole() {
        return roleMapper.getRoleList(new HashMap<>());
    }

    @Override
    public void grant(Role role) {
        roleMapper.deleteRoleResources(role.getId());

        if (!role.getResourceIds().isEmpty()) {
            roleMapper.saveRoleResources(role.getId(), role.getResourceIds());
        }
    }

    @Override
    public DataTable<Role> tables(JsonObject tablePage) {
        PageHelper.offsetPage(tablePage.getInteger("start"), tablePage.getInteger("length"));

        List<Role> roles = roleMapper.getRoleList(new HashMap<>());
        DataTable<Role> tables = new DataTable<>();
        tables.setRecordsTotal(((Page) roles).getTotal());
        tables.setRecordsFiltered(tables.getRecordsTotal());
        tables.setDraw(tablePage.getInteger("draw"));
        tables.setData(roles);
        return tables;
    }

    @Override
    public List<Role> tree() {
        Map<String, Object> params = new HashMap<>();
        List<Role> roles = roleMapper.getRoleList(params);

        return roles;
    }
}
