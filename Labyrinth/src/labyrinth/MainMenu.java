package labyrinth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import score.DBConnection;
import score.Players;

public class MainMenu {

    private JFrame Menu = new JFrame("Labyrinth");
    private JButton Start = new JButton("Play");
    private JButton Exit = new JButton("Exit");
    private JLabel nlabel = new JLabel("Name: ");
    private JTextField name = new JTextField();
    private ArrayList<String> mapList = new ArrayList<String>();
    private ArrayList<String> scores = new ArrayList<String>();
    private int menuWidth = 100;
    private int menuHeight = 30;
    private int menuY = 460;
    private int WIDTH = 490;
    private int HEIGHT = 530;

    public MainMenu() throws SQLException {
        getMapList();

        Menu.setResizable(false);
        Menu.setSize(WIDTH, HEIGHT);
        Menu.setLayout(null);
        Menu.setLocationRelativeTo(null);
        Menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Start.setSize(menuWidth, menuHeight);
        Start.setLocation(10, menuY);
        Start.setEnabled(false);
        Menu.add(Start);
        Start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new Maze(mapList, 0, name.getText(), 0, scores);
                Menu.setVisible(false);
            }

        });

        name.setSize(menuWidth + 35, menuHeight);
        name.setLocation(230, menuY);
        Menu.add(name);

        Exit.setSize(menuWidth, menuHeight);
        Exit.setLocation(375, menuY);
        Menu.add(Exit);
        Exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        nlabel.setSize(menuWidth, menuHeight);
        nlabel.setLocation(185, menuY);
        Menu.add(nlabel);

        name.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                if (name.getText().equals("")) {
                    Start.setEnabled(false);
                } else {
                    Start.setEnabled(true);
                }

            }
        });

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM `scores` ORDER BY `scores`.`points` DESC");

            scores = Players.getPlayers(rs);
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (con != null) {
                con.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        for(int i = 0;i < scores.size() && i < 10; ++i) {
            JLabel tmp = new JLabel();
            tmp.setText(i+1 + ". " + scores.get(i));
            tmp.setSize(menuWidth, menuHeight);
            tmp.setLocation(10, (i * 30) + 5);
            Menu.add(tmp);
        }


        
        Menu.setVisible(true);
    }

    static boolean levelsExistAlready = false;

    public void getMapList() {
        for (int i = 1; i < 11; i++) {
            File map = new File("./Level " + i + ".map");
            if (map.exists()) {
                mapList.add("Level " + i + ".map");
                levelsExistAlready = true;
            }
        }
    }
}
