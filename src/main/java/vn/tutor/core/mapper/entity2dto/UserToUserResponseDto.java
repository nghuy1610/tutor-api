package vn.tutor.core.mapper.entity2dto;

import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import vn.tutor.core.dto.response.UserResp;
import vn.tutor.core.entity.User;
import vn.tutor.core.entity.UserPermission;

import java.util.List;
import java.util.stream.Collectors;

public class UserToUserResponseDto extends PropertyMap<User, UserResp> {
    Converter<List<UserPermission>, List<String>> userPermissionConverter = context ->
        context.getSource().stream().map(userPermission -> userPermission.getPermission().getPermissionType().name())
                .collect(Collectors.toList());

    @Override
    protected void configure() {
        using(userPermissionConverter).map(source.getUserPermissions(), destination.getPermissionType());
    }
}
