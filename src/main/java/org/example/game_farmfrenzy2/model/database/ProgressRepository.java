package org.example.game_farmfrenzy2.model.database;

import java.sql.*;

public class ProgressRepository {

    public void saveProgress(int userId, int levelNumber, int stars, int coins, int timeSpent) {
        String query = """
            INSERT INTO progress (user_id, level_number, stars, coins, time_spent, completed, unlocked)
            VALUES (?, ?, ?, ?, ?, TRUE, TRUE)
            ON DUPLICATE KEY UPDATE
            stars = GREATEST(stars, VALUES(stars)),
            coins = VALUES(coins),
            time_spent = VALUES(time_spent),
            completed = TRUE,
            unlocked = TRUE
        """;

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, levelNumber);
            stmt.setInt(3, stars);
            stmt.setInt(4, coins);
            stmt.setInt(5, timeSpent);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isLevelUnlocked(int userId, int levelNumber) {
        String query = "SELECT unlocked FROM progress WHERE user_id = ? AND level_number = ?";

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, levelNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("unlocked");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return levelNumber == 1;
    }

    public int[] getProgress(int userId) {
        String query = "SELECT level_number, stars FROM progress WHERE user_id = ? AND completed = TRUE";
        int maxLevel = 0;
        int totalStars = 0;

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int level = rs.getInt("level_number");
                int stars = rs.getInt("stars");
                if (level > maxLevel) maxLevel = level;
                totalStars += stars;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{maxLevel, totalStars};
    }

    public void unlockLevel(int userId, int levelNumber) {
        String query = """
            INSERT INTO progress (user_id, level_number, unlocked)
            VALUES (?, ?, TRUE)
            ON DUPLICATE KEY UPDATE unlocked = TRUE
        """;

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection()
                .prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, levelNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}