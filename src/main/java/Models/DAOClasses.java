package Models;

import javax.sql.DataSource;
import java.util.List;

public interface DAOClasses<T> {
    public void setDataSource(DataSource ds);
    public void create(T entity);
    public T getEntity(int id);
    public List<T> listEntities();
}
