package com.flycamel.accountbookserver.api.vertx;

import com.flycamel.accountbookserver.api.dto.UserInfo;
import com.flycamel.accountbookserver.domain.model.User;
import com.flycamel.accountbookserver.domain.service.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserApiVerticle extends AbstractVerticle {

    private JWTAuth jwtAuth;

    private UserService userService;

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void start() throws Exception {
        SessionStore sessionStore = getSessionStore();
        SessionHandler sessionHandler = SessionHandler.create(sessionStore);

        JWTAuthOptions options = new JWTAuthOptions()
                .setKeyStore(new KeyStoreOptions()
                        .setPath("/Users/Youngdong/IdeaProjects/AccountBookServer/src/main/resources/keystore/keystore.jceks")
                        .setPassword("0dong2"));
        jwtAuth = JWTAuth.create(vertx, options);

        Router router = Router.router(vertx);

        router.route().handler(CookieHandler.create());
        router.route().handler(sessionHandler);
        router.route().handler(BodyHandler.create());
        router.route("/user/*").handler(JWTAuthHandler.create(jwtAuth, "/login"));

        router.post("/login").handler(this::login);
        //router.get("/user").handler(this::welcomeUser);
        router.get("/user/getAllUser").handler(this::getAllUser);
        router.post("/user/getUser").handler(this::getUser);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8081);
    }

    private void login(RoutingContext routingContext) {
        String username = routingContext.request().getParam("username");
        String password = routingContext.request().getParam("password");

        if ("yd".equals(username) && "1234".equals(password)) {
            String token = jwtAuth.generateToken(new JsonObject().put("userId", 1).put("role", "USER"), new JWTOptions());
            routingContext.response().putHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token).end("OK");
        } else {
            routingContext.response().setStatusCode(401).end("Wrong username or password");
        }
    }

    private LocalSessionStore getSessionStore() {
        return LocalSessionStore.create(vertx, "AccountBook", 600000);
    }

    private void welcomeUser(RoutingContext routingContext) {
        log.debug("welcomeUser start...");

        Session session = routingContext.session();
        log.info("session id :" + session.id());

        String testValue = session.get("test");
        if (testValue != null) {
            log.info("session test value: {}", testValue);
        } else {
            log.info("add session test value...");
            session.put("test", "testValue");
        }

        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(Json.encode("welcome"));
    }

    private void getUser(RoutingContext routingContext) {
        io.vertx.ext.auth.User authUser = routingContext.user();
        log.info("authUser : {}", authUser.toString());

        Long id = Long.parseLong(routingContext.request().getParam("id"));
        User user = userService.getUser(id);
        UserInfo userInfo = getUserInfoFromUser(user);

        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(Json.encode(userInfo));
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
