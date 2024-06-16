package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;

@Entity
@Getter
@Setter
public class Specialty extends BaseEntity {
    public String name;
}
