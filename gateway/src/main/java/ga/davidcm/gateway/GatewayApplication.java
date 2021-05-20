package ga.davidcm.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {

    @Value("${LOGIN_URL}")
    public String LOGIN_URL;
    @Value("${USERS_URL}")
    public String USERS_URL;
    @Value("${MOVIES_URL}")
    public String MOVIES_URL;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth-service", r -> r.path("/login").uri(LOGIN_URL))
                .route("users-service", r -> r.path("/users/**").uri(USERS_URL))
                .route("movies-service", r -> r
                        .path("/movies/**")
                        .filters(f -> f.requestRateLimiter(conf -> {
                            conf.setRateLimiter(redisRateLimiter());
                            conf.setKeyResolver(customKeyResolver());
                        }))
                        .uri(MOVIES_URL)
                )
                .build();

    }

    @Bean
    RedisRateLimiter redisRateLimiter(){
        //max requests per second
        return new RedisRateLimiter(10,10);
    }

    @Bean
    public KeyResolver customKeyResolver(){
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            String ipAddress = request.getHeaders().getFirst("X-Forwarded-For");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddress().getHostString();
            }

            return Mono.just(ipAddress);
        };
    }

    public static void main(String[] args) {
        //https://spring.io/projects/spring-cloud-gateway#overview
        SpringApplication.run(GatewayApplication.class, args);
    }
}
