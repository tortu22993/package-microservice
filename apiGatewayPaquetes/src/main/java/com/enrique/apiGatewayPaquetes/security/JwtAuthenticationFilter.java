package com.enrique.apiGatewayPaquetes.security;

import com.enrique.apiGatewayPaquetes.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.RouteMatcher;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

   // @Autowired
    //private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;


    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            System.out.println("Request URI: " + exchange.getRequest().getURI().getPath());

            if(validator.isSecured.test(exchange.getRequest())){
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                throw new RuntimeException("Missing authorization header");
            }

            String authHeader=exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);


            if(authHeader!=null && authHeader.startsWith("Bearer ")){
               authHeader=authHeader.substring(7);
            }
            try{
                //REST llam al servicio
               // restTemplate.getForObject("http://LOGIN//validate?token"+authHeader, String.class);
                jwtUtil.validateToken(authHeader);
                List<String> roles = jwtUtil.getRolesFromToken(authHeader);
                //exchange.getAttributes().put("roles", roles);
                // En el API Gateway
                exchange.getRequest().mutate()
                        .header("X-Roles", String.join(",", roles))  // O cualquier formato que necesites
                        .build();

            }catch (Exception e){
                System.out.println("Error validating token");
                throw new RuntimeException("Error validating token");
            }
        }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
