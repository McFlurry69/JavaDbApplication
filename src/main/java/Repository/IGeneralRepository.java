package Repository;


import Models.PersonalInfo;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

public interface IGeneralRepository<T> {
    Future<T> getEntityById(int id) throws SQLException;
    void deleteEntityById(int id);
    void updateEntity (T entity);
    int createEntity (T entity);
    Future<List<T>> getEntities() throws SQLException;
}
