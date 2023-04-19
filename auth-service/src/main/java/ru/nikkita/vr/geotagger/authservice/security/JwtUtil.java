package ru.nikkita.vr.geotagger.authservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nikkita.vr.geotagger.authservice.Utils.Roles;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.livingTimeHours}")
    private int livingTimeHours;
    @Value("${security.jwt.subject}")
    private String subject;
    @Value("${security.jwt.issuer}")
    private String issuer;

    public String generateToken(long userId, List<String> roles){
        Date expirationDate = Date.from(ZonedDateTime.now().plusHours(livingTimeHours).toInstant());
        return JWT.create()
                .withSubject(subject)
                .withClaim("id", userId)
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateGodToken(){
        Date expirationDate = Date.from(ZonedDateTime.now().plusYears(10).toInstant());
        return JWT.create()
                .withSubject(subject)
                .withClaim("roles", Roles.ROLE_ADMIN.name())
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public Map<String, String> validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        Map<String, String> claims = new HashMap<>();
        claims.put("id", jwt.getClaim("id").asString());
        claims.put("role", jwt.getClaim("role").asString());
        return claims;
    }
}
