package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.PermissionType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
public class Permission extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;
}
