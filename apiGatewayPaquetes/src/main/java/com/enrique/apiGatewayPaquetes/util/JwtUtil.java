package com.enrique.apiGatewayPaquetes.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMs;



    public boolean validateToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public  List<String> getRolesFromToken(final String authToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(authToken)
                .getBody();

        // Asegúrate de que los roles estén guardados como una lista
        return (List<String>) claims.get("roles");
    }

}
