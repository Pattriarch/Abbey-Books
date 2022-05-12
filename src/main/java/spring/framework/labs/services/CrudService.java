package spring.framework.labs.services;

import java.util.Set;

public interface CrudService<T, ID> {
    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    T update(T object, ID id);

    void delete(T object);

    void deleteById(ID id);
}