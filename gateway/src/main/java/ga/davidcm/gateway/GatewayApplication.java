package ga.davidcm.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth-service", r -> r.path("/login").uri("http://localhost:5000/login"))
                .route("users-service", r -> r.path("/users/**").uri("http://localhost:5001/"))
                /*.route("movies-service", r -> r
                        .path("/movies/**")
                        .filters(f -> f.requestRateLimiter(conf -> {
                            //conf.setRateLimiter(redisRateLimiter());
                            //conf.setKeyResolver(exchange -> Mono.just("1"));
                        }))
                        .uri("http://localhost:5002/")
                )*/
                .build();

    }

    /*@Bean
    RedisRateLimiter redisRateLimiter(){
        //max request per second
        //max capacity per second
        return new RedisRateLimiter(10,20);
    }*/


    //https://spring.io/projects/spring-cloud-gateway#overview
    /*
        @SpringBootApplication
        public class DemogatewayApplication {
            @Bean
            public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
                return builder.routes()
                    .route("path_route", r -> r.path("/get")
                        .uri("http://httpbin.org"))
                    .route("host_route", r -> r.host("*.myhost.org")
                        .uri("http://httpbin.org"))
                    .route("rewrite_route", r -> r.host("*.rewrite.org")
                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
                        .uri("http://httpbin.org"))
                    .route("hystrix_route", r -> r.host("*.hystrix.org")
                        .filters(f -> f.hystrix(c -> c.setName("slowcmd")))
                        .uri("http://httpbin.org"))
                    .route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
                        .filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
                        .uri("http://httpbin.org"))
                    .route("limit_route", r -> r
                        .host("*.limited.org").and().path("/anything/**")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
                        .uri("http://httpbin.org"))
                    .build();
            }
        }
     */
}
