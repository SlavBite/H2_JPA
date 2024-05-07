package org.example.repo;

import java.sql.SQLException;
import java.util.List;

public interface IRepo<T> {
    void insert(T object) throws SQLException;
    void delete(T object) throws SQLException;
    void update(T object, int id) throws SQLException;
    List<T> getList();
    void executeRequest(String request) throws SQLException;
}
