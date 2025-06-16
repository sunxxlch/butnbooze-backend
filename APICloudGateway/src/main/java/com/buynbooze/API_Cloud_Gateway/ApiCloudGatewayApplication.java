package com.buynbooze.API_Cloud_Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
public class ApiCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder rlb, AuthenticateGatewayFilterFactory aut) {
		return rlb
				.routes()
				.route(p -> p
						.path("/checkoutservice/**")
						.filters(f -> f.removeRequestHeader("Cookie")
								.rewritePath("/checkoutservice/(?<segment>.*)", "/$\\{segment}")
								.filter(aut.apply(new
										AuthenticateGatewayFilterFactory.Config())))
						.uri("lb://checkoutservice")
				)
				.build();
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

}
