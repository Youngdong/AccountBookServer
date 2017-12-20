package com.flycamel.accountbookserver.api.vertx;

import com.flycamel.accountbookserver.api.dto.UserInfo;
import com.flycamel.accountbookserver.domain.model.User;
import com.flycamel.accountbookserver.domain.service.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserApiVerticle extends AbstractVerticle {

    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.get("/user/getAllUser").handler(this::getAllUser);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8081);
    }

    private void getAllUser(RoutingContext routingContext) {
        List<User> userList = userService.getAllUser();

        List<UserInfo> userInfoList = userList.stream()
                .map(this::getUserInfoFromUser)
                .collect(Collectors.toList());

        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(Json.encode(userInfoList));
    }

    private UserInfo getUserInfoFromUser(User user) {
        return UserInfo.builder()
                .id(user.getId())
                .name(user.getName())
                .dateLastLogin(user.getDateLastLogin())
                .build();
    }
}
