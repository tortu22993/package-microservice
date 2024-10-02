package com.enrique.gestionPaquetes.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getRolesFromToken(final String authToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(authToken)
                    .getBody();
            return claims.get("roles", List.class);
        } catch (ExpiredJwtException e) {
            System.out.println("El token ha expirado: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Firma del token no válida: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("El token es malformado: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("Error general al procesar el token: " + e.getMessage());
        }
        return Collections.emptyList(); // Devuelve una lista vacía en caso de error
    }

    public Collection<GrantedAuthority> getAuthoritiesFromRoles(String roles) {
        return Arrays.stream(roles.split(","))
                .map(role -> new SimpleGrantedAuthority(role.trim()))
                .collect(Collectors.toList());
    }

}
