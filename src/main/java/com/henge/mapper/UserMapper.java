package com.henge.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.henge.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User login(Map<String, Object> params);

    int countUserName(String name);

    void save(User user);

    List<User> findUser(Map<String, Object> params);

    List<Long> getUserRoleIds(Long id);

    List<String> getUserRoleNames(Long id);

    User getById(Long id);

    void update(User user);

    void deleteById(Long id);

    void deleteRoles(Long id);

    void saveRoles(@Param("id") Long id, @Param("roleIds") List<Long> roleIds);
}
