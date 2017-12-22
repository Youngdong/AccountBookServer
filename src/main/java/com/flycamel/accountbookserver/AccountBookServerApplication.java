package com.flycamel.accountbookserver;

import com.flycamel.accountbookserver.api.vertx.UserApiVerticle;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
@EnableCaching
@Slf4j
public class AccountBookServerApplication {

	private UserApiVerticle userApiVerticle;

	@Resource
	public void setUserApiVerticle(UserApiVerticle userApiVerticle) {
		this.userApiVerticle = userApiVerticle;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountBookServerApplication.class, args);
	}

	@PostConstruct
	public void deployVerticle() {
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(userApiVerticle);
	}
}
