package vn.tutor.core.dto.response;

import vn.tutor.core.entity.SpecialtyDemand;

public record SpecialtyDemandResp(
    String id,
    String specialtyName,
    String status
) {
  public static SpecialtyDemandResp from(SpecialtyDemand demand) {
    return new SpecialtyDemandResp(demand.getId(), demand.getSpecialtyName(), demand.getStatus().name());
  }
}
