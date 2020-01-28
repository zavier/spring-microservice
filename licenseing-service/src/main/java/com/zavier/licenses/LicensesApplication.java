package com.zavier.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient // 方式1：激活使用 Spring DiscoveryClient
@EnableFeignClients // 方式3：使用feign 客户端
@EnableCircuitBreaker
public class LicensesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LicensesApplication.class, args);
	}

	// 方式2：使用带有 Ribbon 功能的 Spring RestTemplate
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
