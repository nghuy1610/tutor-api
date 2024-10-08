package vn.tutor.core.filter;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.tutor.core.security.CustomUserDetails;
import vn.tutor.core.security.JwtTokenInfo;
import vn.tutor.core.security.JwtUtils;

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
      JwtTokenInfo jwtTokenInfo = jwtUtils.validateToken(token);
      CustomUserDetails customUserDetails
          = (CustomUserDetails) customUserDetailsService.loadUserByUsername(jwtTokenInfo.email());
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          customUserDetails, null, customUserDetails.getAuthorities());
      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}
