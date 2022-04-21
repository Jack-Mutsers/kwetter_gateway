package com.kwetter.gateway.controllers;

import com.kwetter.gateway.components.AuthFilter;
import com.kwetter.gateway.configurations.UriConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.kwetter.gateway.components.AuthFilter.Config;

@EnableConfigurationProperties(UriConfiguration.class)
@RestController
public class RouterController {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        Config config = new Config();
        return builder.routes()

                // auth service
                .route(p -> p
                        .path("/user/register")
                        .uri("http://localhost:9091"))
                .route(p -> p
                        .path("/user/update")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9091"))
                .route(p -> p
                        .path("/user/changePassword")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9091"))
                .route(p -> p
                        .path("/auth/login")
                        .uri("http://localhost:9091"))

                // posting service
                .route(p -> p
                        .path("/post/get/*")
                        .uri("http://localhost:9092"))
                .route(p -> p
                        .path("/post/user_posts/*")
                        .uri("http://localhost:9092"))
                .route(p -> p
                        .path("/post/group_posts/*")
                        .uri("http://localhost:9092"))
                .route(p -> p
                        .path("/post/delete/*")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9092"))
                .route(p -> p
                        .path("/post/create")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9092"))
                .route(p -> p
                        .path("/post/update")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9092"))

                .route(p -> p
                        .path("/comment/delete/*")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9092"))
                .route(p -> p
                        .path("/comment/create")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9092"))
                .route(p -> p
                        .path("/comment/update")
                        .filters(f -> f.filter(authFilter.apply(config)))
                        .uri("http://localhost:9092"))



                .build();
    }

}
