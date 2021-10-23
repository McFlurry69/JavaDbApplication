package Repository;

import Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IGeneralRepository<T> {
    public T returnEntityById(int id);
}
