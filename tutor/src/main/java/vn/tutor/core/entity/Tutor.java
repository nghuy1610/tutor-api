package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.enums.Gender;
import vn.tutor.core.enums.VerificationStatus;

import javax.persistence.*;
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

    @ManyToMany
    @JoinTable(name = "tutor_specialty", joinColumns = @JoinColumn(name="tutor_id"),
            inverseJoinColumns = @JoinColumn(name="specialty_id"))
    private List<Specialty> specialties;
}
