package com.henge.service;

import com.henge.model.User;
import com.henge.vo.DataTable;
import io.vertx.core.json.JsonObject;

import java.util.List;

public interface UserService {

    User login(User user);

    void add(User user);

    User get(Long id);

    void edit(User user);

    void delete(Long id);

    void grant(User user);

    List<String> resourceList(Long id);

    void editPwd(User user);

    boolean editCurrentUserPwd(Long currUid, String oldPwd, String pwd);

    List<String> getUserRoleNames(Long id);

    DataTable<User> tables(JsonObject tablePage);
}
