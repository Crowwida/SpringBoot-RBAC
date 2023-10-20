package com.henge.controller;

import com.henge.jwt.AuthTokenDetails;
import com.henge.jwt.JsonWebTokenUtility;
import com.henge.model.RestResp;
import com.henge.model.User;
import com.henge.service.UserService;
import com.henge.util.SessionUtil;
import com.henge.vo.DataTable;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private JsonWebTokenUtility tokenService = new JsonWebTokenUtility();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User u) {
        JsonObject json = new JsonObject();

        User user = userService.login(u);
        if (user != null) {
            AuthTokenDetails authTokenDetails = new AuthTokenDetails();
            authTokenDetails.setId(user.getId());
            authTokenDetails.setUsername(user.getUsername());
            authTokenDetails.setExpirationDate(buildExpirationDate());
            authTokenDetails.setRoleNames(userService.getUserRoleNames(user.getId()));

            // Create auth token
            String jwt = tokenService.createJsonWebToken(authTokenDetails);
            if (jwt != null) {
                json.put("token", jwt);
                json.put("userId", user.getId());
                json.put("name", user.getName());
                json.put("resourceList", userService.resourceList(user.getId()));
            }
        } else {
            throw new RuntimeException("用户名或密码错误");
        }
        return json.encode();
    }

    private Date buildExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        return calendar.getTime();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User add(@RequestBody User user) {
        userService.add(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public User edit(@RequestBody User user) {
        userService.edit(user);
        return user;
    }

    @RequestMapping(value = "/tables", method = RequestMethod.POST)
    public DataTable<User> tables(@RequestBody String tablePage) {
        return userService.tables(new JsonObject(tablePage));
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("userId") Long userId) {
        User user = userService.get(userId);
        user.setPassword(null);
        return user;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public RestResp delete(@PathVariable("userId") Long userId) {
        Long currUid = SessionUtil.getCurrUid();
        if (Objects.equals(userId, currUid)) {// 不能删除自己
            return RestResp.error(RestResp.ERROR, "不能删除自己");
        }
        userService.delete(userId);
        return RestResp.ok("删除成功");
    }

    /**
     * 批量删除用户
     *
     * @param ids ('0','1','2')
     * @return
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
    public void batchDelete(String ids) {
        if (ids != null && ids.length() > 0) {
            for (String id : ids.split(",")) {
                this.delete(Long.valueOf(id));
            }
        }
    }

    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    @ResponseBody
    public RestResp grant(@RequestBody User user) {
        userService.grant(user);
        return RestResp.ok("授权成功！");
    }

    @RequestMapping("/editPwd")
    public RestResp editPwd(@RequestBody User user) {
        userService.editPwd(user);
        return RestResp.ok("编辑成功");
    }

    @RequestMapping(value = "/editCurrentUserPwd", method = RequestMethod.POST)
    @ResponseBody
    public RestResp editCurrentUserPwd(@RequestBody User user) {
        Long currUid = SessionUtil.getCurrUid();

        if (currUid != null) {
            if (!userService.editCurrentUserPwd(currUid, user.getOldPassword(), user.getPassword())) {
                throw new RuntimeException("原密码错误！");
            }
        } else {
            throw new RuntimeException("登录超时，请重新登录！");
        }
        return RestResp.ok("修改成功");
    }
}
