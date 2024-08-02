package vn.tutor.core.dto.request;

import java.util.List;

public record TutorReq(
    String introduction,
    String address,
    String gender,
    List<String> specialties
) {

}
