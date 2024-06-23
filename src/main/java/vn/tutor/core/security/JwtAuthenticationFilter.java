package vn.tutor.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);

  private final JwtUtils jwtUtils;
  private final UserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7);
      AuthToken authToken = jwtUtils.validateToken(token);
      UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(authToken.email());
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          customUserDetails.getUsername(),
          customUserDetails.getPassword(), customUserDetails.getAuthorities());
      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}
