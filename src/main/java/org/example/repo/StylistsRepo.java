package org.example.repo;

import org.example.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.List;

public class StylistsRepo implements IRepo<Stylists> {
    public EntityManager em =
            Persistence.createEntityManagerFactory("TST").createEntityManager();

    @Override
    public void insert(Stylists stylist)  throws SQLException {
        String query = "INSERT INTO stylists (name, isDeleted) VALUES (?, ?)";
        try (Connection conn = this.connectToDB();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            Statement stmt = this.getStatement(this.connectToDB());
            statement.setString(1, stylist.getName());
            statement.setBoolean(2, stylist.isDeleted());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    stylist.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to get generated id for client.");
                }
            }
        }
    }


//    @Override
//    public Integer insert(Stylists stylist) throws SQLException {
//        String str = String.format("INSERT INTO stylists (name, isDeleted) VALUES ('%s', %s)",
//                stylist.getName(),
//                stylist.isDeleted());
//        this.executeRequest(str);
//        try (ResultSet rs = this.getStatement(this.connectToDB()).executeQuery("SELECT MAX(id) FROM Stylists")) {
//            this.closeConnection(this.getStatement(this.connectToDB()));
//            while (rs.next()) {
//                return rs.getInt(1);
//            }
//            return -1;
//        }
//    }
    @Override
    public void delete(Stylists stylists) throws SQLException {
        String str = String.format("UPDATE Stylists SET isDeleted = true WHERE id = %s" , stylists.getId());
        this.executeRequest(str);
    }
    @Override
    public void update(Stylists stylists, int id) throws SQLException {
        String str = String.format("UPDATE stylists SET id = %s, name = '%s' WHERE id = %s" ,
                stylists.getId(),
                stylists.getName(),
                stylists.getId());
        this.executeRequest(str);
    }
    @Override
    public void executeRequest(String request) throws SQLException {
        Statement stmt = this.getStatement(this.connectToDB());
        stmt.execute(request);
        stmt.close();
    }
    @Override
    public List<Stylists> getList() {
        TypedQuery<Stylists> namedQuery = em.createNamedQuery("Stylists.getAll", Stylists.class);
        return namedQuery.getResultList();
    }
    public Connection connectToDB() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/mem:beautysaloon", "sa", "");
        if (conn==null) {
            System.exit(0);
        }
        return conn;
    }
    public Statement getStatement(Connection conn) throws SQLException {
        return conn.createStatement();
    }
    public void closeConnection(Statement stmt) throws SQLException {
        stmt.close();
    }
}
