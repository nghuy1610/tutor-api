package vn.tutor.core.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "tutor_specialty")
@Getter
@Setter
public class TutorSpecialty extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
}
