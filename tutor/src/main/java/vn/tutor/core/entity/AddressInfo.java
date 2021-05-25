package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class AddressInfo extends BaseEntity{
    private String province;
    private String district;
}
