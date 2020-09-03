package score;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class modifyPlayer {

    public modifyPlayer() {

    }

    public void modify(String points, String name) {
        String sql = "UPDATE scores SET points = ? where name = ?;";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, points);
            stmt.setString(2, name);
            stmt.execute();

        } catch (Exception e) {

        }
    }
}
