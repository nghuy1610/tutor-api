package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class ClassOrder extends BaseEntity {
    private String description;

    @ManyToMany
    @JoinTable(name = "class_order_specialty", joinColumns = @JoinColumn(name="class_order_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    List<Specialty> specialties;
}
