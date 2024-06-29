package vn.tutor.core.security;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.tutor.core.entity.User;
import vn.tutor.core.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findFullUserByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    List<String> authorities = user.getUserPermissions().stream()
        .map(up -> up.getPermission().getPermissionType().name())
        .toList();
    return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), authorities);
  }
}
