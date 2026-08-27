package org.example.game_farmfrenzy2.model.database;

import java.sql.*;

public class UserRepository {

    public boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("Username already exists!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getUserId(String username) {
        String query = "SELECT id FROM users WHERE username = ?";

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}