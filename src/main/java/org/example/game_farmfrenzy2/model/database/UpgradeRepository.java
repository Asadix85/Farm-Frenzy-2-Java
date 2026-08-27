package org.example.game_farmfrenzy2.model.database;

import java.sql.*;

public class UpgradeRepository {

    public void saveUpgradeLevel(int userId, String upgradeType, int level) {
        String query = """
            INSERT INTO upgrades (user_id, upgrade_type, level)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE level = ?
        """;

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, upgradeType);
            stmt.setInt(3, level);
            stmt.setInt(4, level);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUpgradeLevel(int userId, String upgradeType) {
        String query = "SELECT level FROM upgrades WHERE user_id = ? AND upgrade_type = ?";

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, upgradeType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("level");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
}