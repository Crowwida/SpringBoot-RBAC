package com.henge.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.henge.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    void save(Role role);

    Role getById(Long id);

    void update(Role role);

    List<Role> getRoleList(Map<String, Object> params);

    void deleteById(Long id);

    void deleteRoleResources(Long id);

    void saveRoleResources(@Param("id") Long id, @Param("resourceIds") List<Long> resourceIds);
}
