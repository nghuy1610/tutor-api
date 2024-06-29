package vn.tutor.core.security;

import java.util.List;

public record JwtTokenInfo(String userId, String email, List<String> authorities) {
}
