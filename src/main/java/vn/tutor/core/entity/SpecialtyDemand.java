package vn.tutor.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.dto.request.SpecialtyDemandAssessmentReq;
import vn.tutor.core.dto.request.SpecialtyDemandReq;
import vn.tutor.core.enums.SpecialtyDemandStatus;

@Entity
@Table(name = "specialty_demand")
@Getter
@Setter
public class SpecialtyDemand extends BaseEntity {
  @Column(name = "specialty_name")
  private String specialtyName;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private SpecialtyDemandStatus status;

  public static SpecialtyDemand from(SpecialtyDemandReq specialtyReq) {
    SpecialtyDemand specialtyDemand = new SpecialtyDemand();
    specialtyDemand.setSpecialtyName(specialtyReq.specialtyName());
    specialtyDemand.setStatus(SpecialtyDemandStatus.PENDING);
    return specialtyDemand;
  }

  public void apply(SpecialtyDemandAssessmentReq request) {
    this.status = SpecialtyDemandStatus.valueOf(request.status());
    this.specialtyName = request.specialtyName();
  }
}
