package vn.tutor.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import vn.tutor.core.dto.request.SpecialtyReq;

@Entity
@Table(name = "specialty")
@Getter
@Setter
public class Specialty extends BaseEntity {

  @Column(name = "name")
  public String name;

  public static Specialty from(SpecialtyReq specialtyReq) {
    Specialty specialty = new Specialty();
    specialty.setName(specialtyReq.name());
    return specialty;
  }
}
