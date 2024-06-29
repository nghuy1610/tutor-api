package vn.tutor.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
  private static final long EXPIRATION = 600000;
  private static final String ISSUER = "api.tutor.vn";
  private static final String EMAIL_CLAIM = "email";
  private static final String ROLE_CLAIM = "roles";
  private final String secret;

  public JwtUtils(@Value("${jwt.secret}") String secret) {
    this.secret = secret;
  }

  public String generateToken(JwtTokenInfo jwtTokenInfo) {
    return JWT.create()
        .withSubject(jwtTokenInfo.userId())
        .withIssuer(ISSUER)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
        .withClaim(EMAIL_CLAIM, jwtTokenInfo.email())
        .withClaim(ROLE_CLAIM, jwtTokenInfo.authorities())
        .sign(Algorithm.HMAC256(secret));
  }

  public JwtTokenInfo validateToken(String token) {
    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
        .withIssuer(ISSUER).build();
    DecodedJWT jwt = jwtVerifier.verify(token);
    String userId = jwt.getSubject();
    String email = jwt.getClaim(EMAIL_CLAIM).asString();
    List<String> authorities = jwt.getClaim(ROLE_CLAIM).asList(String.class);
    return new JwtTokenInfo(userId, email, authorities);
  }
}
