package vn.tutor.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Version
    private Integer version;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    private ZonedDateTime createdOn;

    @LastModifiedDate
    private ZonedDateTime updatedOn;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity otherEntity = (BaseEntity) obj;
        return Objects.equals(id, otherEntity.getId()) && Objects.equals(version, otherEntity.getVersion())
                && Objects.equals(createdBy, otherEntity.getCreatedBy()) && Objects.equals(updatedBy, otherEntity.getUpdatedBy())
                && Objects.equals(createdOn, otherEntity.getCreatedOn()) && Objects.equals(updatedOn, otherEntity.getUpdatedOn());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(version).append(createdBy).append(updatedBy).append(createdOn).append(updatedOn)
                .build();
    }
}
