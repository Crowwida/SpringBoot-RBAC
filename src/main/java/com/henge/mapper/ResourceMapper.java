package com.henge.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.henge.model.Resource;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    List<Resource> getResourceList(Map<String, Object> params);

    void save(Resource resource);

    void deleteById(Long id);

    void update(Resource resource);

    Resource getById(Long id);

    List<Long> getRoleResourceIds(Long roleId);

    List<String> getRoleResourceNames(Long roleId);
}
