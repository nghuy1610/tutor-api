package unit.vn.tutor.core.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.tutor.core.security.JwtTokenInfo;
import vn.tutor.core.security.JwtUtils;

public class JwtUtilsTest {
  JwtUtils jwtUtils;
  String secret = "secret";

  @BeforeEach
  void init() {
    jwtUtils = new JwtUtils(secret);
  }

  @Test
  void givenJwtTokenInfo_whenGenerateJwtToken_thenReturnTokenWithCorrectClaim() {
    JwtTokenInfo jwtTokenInfo = new JwtTokenInfo("id", "email@mail.com", List.of("authority"));

    String token = jwtUtils.generateToken(jwtTokenInfo);

    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
        .build();
    DecodedJWT jwt = jwtVerifier.verify(token);

    assertThat(jwt.getSubject()).isEqualTo("id");
    assertThat(jwt.getIssuer()).isEqualTo("api.tutor.vn");
    assertThat(jwt.getClaim("email").asString()).isEqualTo("email@mail.com");
    assertThat(jwt.getClaim("roles").asList(String.class)).isEqualTo(List.of("authority"));
    assertThat(jwtUtils.validateToken(token)).isEqualTo(jwtTokenInfo);
  }
}
