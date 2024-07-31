package vn.tutor.core.enums;

public enum UserType {
  STUDENT, TUTOR, OPERATOR, SUPER_ADMIN;

  public static boolean isNormalUserType(String permission) {
    return "TUTOR".equalsIgnoreCase(permission) || "STUDENT".equalsIgnoreCase(permission);
  }

  public static boolean isOperatorUserType(String permission) {
    return "OPERATOR".equalsIgnoreCase(permission);
  }
}
