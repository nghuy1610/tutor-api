package vn.tutor.core.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tutor.core.dto.request.UserRequestDto;
import vn.tutor.core.dto.response.UserResponseDto;
import vn.tutor.core.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(path = "", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponseDto> createAndRetrieveUser(@RequestBody @Valid UserRequestDto requestDto) {
         UserResponseDto responseDto = userService.createAndRetrieveOperator(requestDto);
         return ResponseEntity.ok(responseDto);
    }
}
