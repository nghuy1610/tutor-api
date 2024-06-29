package vn.tutor.core.enums;

public enum PermissionType {
  SUPER_ADMIN,
  OPERATOR,
  TUTOR,
  STUDENT;

  public static boolean isNormalUserPermission(String permission) {
    return "TUTOR".equalsIgnoreCase(permission) || "STUDENT".equalsIgnoreCase(permission);
  }

  public static boolean isOperatorPermission(String permission) {
    return "OPERATOR".equalsIgnoreCase(permission);
  }
}
