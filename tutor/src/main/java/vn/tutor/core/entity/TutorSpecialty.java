package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class TutorSpecialty extends BaseEntity{
    @ManyToOne
    Tutor tutor;

    @ManyToOne
    Specialty specialty;
}
