package Repository;


public interface IGeneralRepository<T> {
    public T getEntityById(int id);
    public boolean deleteEntityById(int id);
    public T updateEntity (T entity);
    public int createEntity (T entity);
}
