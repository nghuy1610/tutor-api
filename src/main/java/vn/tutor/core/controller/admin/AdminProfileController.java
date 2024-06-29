package vn.tutor.core.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/profiles")
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'OPERATOR')")
public class AdminProfileController {

}
