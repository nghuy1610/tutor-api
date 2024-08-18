package vn.tutor.core.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import vn.tutor.core.entity.Tutor;

public interface TutorCustomRepository {

  Page<Tutor> findTutorBySpecialtiesAndAddresses(List<String> specialties, List<String> addresses, int pageNum, int pageSize);
}
