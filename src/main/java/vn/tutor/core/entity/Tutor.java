package vn.tutor.core.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.Gender;
import vn.tutor.core.enums.VerificationStatus;

@Entity
@Table(name = "tutor")
@Getter
@Setter
public class Tutor extends BaseEntity {

  @Column(name = "introduction")
  private String introduction;

  @Column(name = "address")
  private String address;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "verification_status")
  @Enumerated(EnumType.STRING)
  private VerificationStatus verificationStatus;

  @OneToMany(mappedBy = "tutor")
  private List<TutorSpecialty> tutorSpecialties;

  @OneToOne(cascade = CascadeType.ALL)
  private User user;

}
