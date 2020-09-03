package score;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class addPlayer {

    public addPlayer() {

    }

    public void add(String name, String points) {
        String sql = "INSERT INTO `scores` (`name`, `points`) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, name);
            stmt.setString(2, points);

            stmt.execute();

        } catch (Exception e) {

        }
    }
}
