package dao;


import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GeneralRepository<T extends Object> {
    CompletableFuture<T> getEntityByIdAsync(int id) throws SQLException;
    CompletableFuture<Integer> deleteEntityByIdAsync(int id) throws SQLException;
    CompletableFuture<Integer> updateEntityAsync(T entity) throws SQLException;
    CompletableFuture<Integer> createEntityAsync(T entity) throws SQLException;
    CompletableFuture<List<T>> getEntitiesAsync() throws SQLException;
}
