package com.henge.service;

import com.henge.model.Resource;
import com.henge.model.Menu;
import java.util.List;
import java.util.Map;

public interface ResourceService {

    public List<Menu> menus();

    public List<Menu> allMenus();

    public List<Resource> treeList();

    public void add(Resource resource);

    public void delete(Long id);

    public void edit(Resource resource);

    public Resource get(Long id);

    List<Resource> getResourceList(Map<String, Object> params);
}
