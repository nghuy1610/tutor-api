package vn.tutor.core.config;

import jakarta.servlet.DispatcherType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import vn.tutor.core.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private static final Logger LOGGER = LogManager.getLogger(SecurityConfig.class);
  public static final String[] WHITE_LISTS = {
      "/api/users/login", "/api/users"
  };
  private final UserDetailsService customUserDetailsService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
    http
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
        .rememberMe(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(registry -> registry.requestMatchers(WHITE_LISTS).permitAll()
            .dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
            .anyRequest().authenticated())
        .userDetailsService(customUserDetailsService)
        .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
            .authenticationEntryPoint(
                (request, response, authException) -> {
                  handlerExceptionResolver.resolveException(request, response, null, authException);
                }
            ))
        .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .maximumSessions(1));
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager configureAuthentication(HttpSecurity http, PasswordEncoder bcryptPasswordEncoder)
      throws Exception {
    var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(customUserDetailsService).passwordEncoder(bcryptPasswordEncoder);
    return builder.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern(CorsConfiguration.ALL);
    configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
    configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
