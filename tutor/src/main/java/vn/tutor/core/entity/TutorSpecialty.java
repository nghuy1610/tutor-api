package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class TutorSpecialty extends BaseEntity{
    @ManyToOne
    Tutor tutor;

    @ManyToOne
    Specialty specialty;
}
