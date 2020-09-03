package score;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Players {

    public static ArrayList<String> getPlayers(ResultSet rs) throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        while (rs.next()) {
            StringBuffer buffer = new StringBuffer();

            buffer.append(rs.getString("name") + " ");
            buffer.append(rs.getString("points"));


            list.add(buffer.toString());
        }
        return list;
    }
}

