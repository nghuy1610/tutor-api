package vn.tutor.core.dto.request;

public record UserProfileReq(
    String phoneNumber,
    String firstName,
    String lastName
) {

}
