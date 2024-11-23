package course_project.back.repositories;

import java.util.List;

public interface IRepository<T> {
    public void add(T object);
    public T find(long ID);
    public List getAll();
    public void update(T updatedObject);
    public void delete(T object);
}