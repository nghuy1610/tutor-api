package vn.tutor.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.PermissionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "permission")
@Getter
@Setter
public class Permission extends BaseEntity{
    @Column(name = "permission_type")
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;
}
