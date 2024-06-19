package vn.tutor.core.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.Gender;
import vn.tutor.core.enums.VerificationStatus;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student extends BaseEntity {
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

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;
}
