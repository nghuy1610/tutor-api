package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.Gender;
import vn.tutor.core.enums.VerificationStatus;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Tutor extends BaseEntity {
    private String introduction;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    @OneToMany
    private List<TutorSpecialty> tutorSpecialties;
}
