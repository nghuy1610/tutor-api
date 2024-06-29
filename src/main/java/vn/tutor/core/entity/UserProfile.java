package vn.tutor.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.tutor.core.dto.request.UserProfileReq;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
public class UserProfile extends BaseEntity {
  @Column(name = "email")
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @OneToOne()
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public void apply(UserProfileReq userProfileReq) {
    this.phoneNumber = userProfileReq.phoneNumber();
    this.firstName = userProfileReq.firstName();
    this.lastName = userProfileReq.lastName();
  }
}
