package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.PermissionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Getter
@Setter
public class Permission extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;
}
