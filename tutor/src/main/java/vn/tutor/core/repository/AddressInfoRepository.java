package vn.tutor.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.tutor.core.entity.AddressInfo;

public interface AddressInfoRepository extends JpaRepository<AddressInfo, String> {
}
