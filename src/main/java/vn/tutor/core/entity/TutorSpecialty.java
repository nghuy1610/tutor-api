package vn.tutor.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tutor_specialty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorSpecialty extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "tutor_id")
  private Tutor tutor;

  @ManyToOne
  @JoinColumn(name = "specialty_id")
  private Specialty specialty;
}
