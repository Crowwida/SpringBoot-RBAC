package com.henge.service;

import com.henge.model.Role;
import com.henge.vo.DataTable;
import io.vertx.core.json.JsonObject;

import java.util.List;

public interface RoleService {

	void add(Role role);

	Role get(Long id);

	void edit(Role role);

	List<Role> treeGrid();

	void delete(Long id);

	List<Role> roles();

	List<Role> allRole();

	void grant(Role role);

    DataTable<Role> tables(JsonObject tablePage);

	List<Role> tree();
}
