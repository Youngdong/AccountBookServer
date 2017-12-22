package com.flycamel.accountbookserver;

import com.flycamel.accountbookserver.api.vertx.UserApiVerticle;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
@EnableCaching
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

	@Bean
	HazelcastInstance hazelcastCacheInstance() {
		ClientConfig config = new ClientConfig();
		config.getGroupConfig().setName("dev").setPassword("dev-pass");
		config.getNetworkConfig().addAddress("localhost");
		config.setInstanceName("hazelcastQueryCache");
		HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
		return instance;
	}

	@Bean
	CacheManager cacheManager() {
		return new HazelcastCacheManager(hazelcastCacheInstance());
	}
}
