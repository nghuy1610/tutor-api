package vn.tutor.core.dto.response;

import java.util.List;

public record LoginResponseDto(String token, List<String> roles) {

}
