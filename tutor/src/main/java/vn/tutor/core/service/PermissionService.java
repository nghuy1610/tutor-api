package vn.tutor.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tutor.core.dto.request.PermissionRequestDto;
import vn.tutor.core.dto.response.PermissionResponseDto;
import vn.tutor.core.entity.Permission;
import vn.tutor.core.enums.PermissionType;
import vn.tutor.core.mapper.Mapper;
import vn.tutor.core.repository.PermissionRepository;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final Mapper mapper;

    public Permission findPermissionByType(PermissionType permissionType) {
        return permissionRepository.findByPermissionType(permissionType);
    }

    public PermissionResponseDto createAndRetrievePermission(PermissionRequestDto requestDto) {
        Permission permission = mapper.map(requestDto, Permission.class);
        permissionRepository.save(permission);
        return mapper.map(permission, PermissionResponseDto.class);
    }
}
