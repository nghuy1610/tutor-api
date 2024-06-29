package vn.tutor.core.dto.response;

import java.util.List;

public record LoginResp(String token, List<String> roles) {

}
