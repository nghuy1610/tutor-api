package vn.tutor.core.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import vn.tutor.core.mapper.entity2dto.UserToUserResponseDto;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class Mapper {
    private final ModelMapper mapper;

    public Mapper() {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.addMappings(new UserToUserResponseDto());
    }

    public <S, D> D map(S source, Class<D> destClass) {
        return mapper.map(source, destClass);
    }

    public <S, D> void apply(S source, D dest) {
        mapper.map(source, dest);
    }

    public <S, D> List<D> mapToList(List<S> source, Type type) {
        return mapper.map(source, type);
    }
}
