package vn.tutor.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;

@Entity
@Table(name = "specialty")
@Getter
@Setter
public class Specialty extends BaseEntity {
    @Column(name = "name")
    public String name;
}
