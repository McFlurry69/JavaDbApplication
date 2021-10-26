package Repository;


import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IGeneralRepository<T extends Object> {
    CompletableFuture<T> getEntityById(int id) throws SQLException;
    CompletableFuture<Integer> deleteEntityById(int id) throws SQLException;
    CompletableFuture<Integer> updateEntity (T entity) throws SQLException;
    CompletableFuture<Integer> createEntity (T entity) throws SQLException;
    CompletableFuture<List<T>> getEntities() throws SQLException;
}
