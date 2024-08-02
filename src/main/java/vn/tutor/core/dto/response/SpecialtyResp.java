package vn.tutor.core.dto.response;

import vn.tutor.core.entity.Specialty;

public record SpecialtyResp(
    String id,
    String name
) {
  public static SpecialtyResp from(Specialty specialty) {
    return new SpecialtyResp(specialty.getId(), specialty.getName());
  }
}
