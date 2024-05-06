package org.example.repo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface IRepo<T> {
    Integer insert(T object) throws SQLException;
    void delete(T object) throws SQLException;
    void update(T object, int id) throws SQLException;
    List<T> getList();
    void executeRequest(String request) throws SQLException;
}
