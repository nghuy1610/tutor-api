package vn.tutor.core.enums;

public enum PermissionType {
  SUPER_ADMIN,
  OPERATOR,
  TUTOR,
  STUDENT;

  public static PermissionType fromUserType(UserType userType) {
    return switch (userType) {
      case SUPER_ADMIN -> SUPER_ADMIN;
      case OPERATOR -> OPERATOR;
      case TUTOR -> TUTOR;
      case STUDENT -> STUDENT;
    };
  }
}
