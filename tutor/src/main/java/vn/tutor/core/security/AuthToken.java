package vn.tutor.core.security;

import java.util.List;

public record AuthToken(String userId, String email, List<String> authorities) {
}
