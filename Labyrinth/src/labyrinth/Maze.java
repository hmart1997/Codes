package labyrinth;

import player.Player;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import player.Dragon;
import score.addPlayer;
import score.modifyPlayer;

public class Maze extends JFrame implements ActionListener {

    public static int rows = 20;
    public static int columns = 20;
    public static int panelSize = 25;
    public static int map[][] = new int[columns][rows];
    public static int endLevelLoc;
    private static Tile maptiles[][] = new Tile[columns][rows];
    Player p;
    Dragon d;
    private Timer t = new Timer(1000, this);
    private int time = 0;
    private Random rand = new Random();
    private int random = rand.nextInt(4) + 1;
    private JLabel tlabel = new JLabel("Time: " + time);

    public Maze(ArrayList<String> mapList, int level, String name, int points, ArrayList<String> scores) {
        if(!onList(name,scores)) {
            addPlayer add = new addPlayer();
            add.add(name,"0");
            scores.add(name + " " + String.valueOf(points));
        } else if (onList(name,scores) && points == 0){
            points = onPoints(name,scores);
        }
        p = new Player(name, points);
        loadMap(mapList.get(level));
        this.setResizable(false);
        this.setSize((columns * panelSize) + 50, (rows * panelSize) + 70);
        this.setTitle("Labyrinth");
        this.setLayout(null);
        t.start();
        time = 0;
        JLabel plabel = new JLabel("Name: " + name + " Points: " + p.getPoints());
        plabel.setSize(250, 30);
        plabel.setLocation(0, 0);
        this.add(plabel);
        tlabel.setSize(100, 30);
        tlabel.setLocation(490, 0);
        this.add(tlabel);

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                revalidate();
                repaint();

                if (key == KeyEvent.VK_W) {
                    p.moveUp();
                }
                if (key == KeyEvent.VK_A) {
                    p.moveLeft();
                }
                if (key == KeyEvent.VK_S) {
                    p.moveDown();
                }
                if (key == KeyEvent.VK_D) {
                    p.moveRight();
                }

                if (p.x == columns - 1 && p.y == endLevelLoc) {
                    t.stop();
                    JOptionPane.showMessageDialog(null, "Congratulations, you've beaten the level under " + time + "s.", "End Game", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    p.setPoints(p.getPoints() + 1);
                    modifyPlayer mod = new modifyPlayer();
                    mod.modify(String.valueOf(p.getPoints()),name);
                    if (level == 9) {
                        new Maze(mapList, 0, name, p.getPoints(), scores);
                    } else {
                        new Maze(mapList, level + 1, name, p.getPoints(), scores);
                    }
                }
                if (p.x + 1 == d.x && p.y == d.y) {
                    t.stop();
                    JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    try {
                        new MainMenu();
                    } catch (SQLException ex) {
                        Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (p.x == d.x && p.y + 1 == d.y) {
                    t.stop();
                    JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    try {
                        new MainMenu();
                    } catch (SQLException ex) {
                        Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (p.x == d.x && p.y - 1 == d.y) {
                    t.stop();
                    JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    try {
                        new MainMenu();
                    } catch (SQLException ex) {
                        Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (p.x - 1 == d.x && p.y == d.y) {
                    t.stop();
                    JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    try {
                        new MainMenu();
                    } catch (SQLException ex) {
                        Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                for (int i = 0; i < maptiles.length; ++i) {
                    for (int j = 0; j < maptiles.length; ++j) {
                        maptiles[i][j].setBackground(Color.BLACK);
                    }
                }
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        if (p.y - j >= 0 && p.x + i < 20) {
                            if (map[p.x + i][p.y - j] == 0) {
                                maptiles[p.x + i][p.y - j].setBackground(Color.GRAY);
                            } else {
                                maptiles[p.x + i][p.y - j].setBackground(Color.WHITE);
                            }
                        }
                        if (p.y + j < 20 && p.x + i < 20) {
                            if (map[p.x + i][p.y + j] == 0) {
                                maptiles[p.x + i][p.y + j].setBackground(Color.GRAY);
                            } else {
                                maptiles[p.x + i][p.y + j].setBackground(Color.WHITE);
                            }
                        }
                        if (p.y - j >= 0 && p.x - i >= 0) {
                            if (map[p.x - i][p.y - j] == 0) {
                                maptiles[p.x - i][p.y - j].setBackground(Color.GRAY);
                            } else {
                                maptiles[p.x - i][p.y - j].setBackground(Color.WHITE);
                            }
                        }
                        if (p.y + j < 20 && p.x - i >= 0) {
                            if (map[p.x - i][p.y + j] == 0) {
                                maptiles[p.x - i][p.y + j].setBackground(Color.GRAY);
                            } else {
                                maptiles[p.x - i][p.y + j].setBackground(Color.WHITE);
                            }
                        }
                    }
                }
                if (maptiles[d.x][d.y].getBackground() == Color.BLACK) {
                    d.setBackground(Color.BLACK);
                } else {
                    d.setBackground(Color.RED);
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {

            }

            @Override
            public void keyTyped(KeyEvent arg0) {

            }

        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setLocationRelativeTo(null);

        
        p.setVisible(true);
        this.add(p);

        d = new Dragon();
        d.setVisible(true);
        this.add(d);

        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                Tile tile = new Tile(x, y);
                tile.setSize(panelSize, panelSize);
                tile.setLocation((x * panelSize) + 23, (y * panelSize) + 25);
                tile.setBackground(Color.BLACK);
                maptiles[x][y] = tile;
                if (map[x][y] == 1) {
                    tile.setWall(false);
                    if (x > 4 && x < 15) {
                        d.setLocation((x * panelSize) + 23, (y * panelSize) + 25);
                        d.x = x;
                        d.y = y;
                    }
                    if (x == columns - 1) {
                        endLevelLoc = y;
                    }
                }
                p.setLocation(23, (19 * panelSize) + 25);
                p.x = 0;
                p.y = 19;

                tile.setVisible(true);
                this.add(tile);
            }
        }
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (p.y - j >= 0 && p.x + i < 20) {
                    if (map[p.x + i][p.y - j] == 0) {
                        maptiles[p.x + i][p.y - j].setBackground(Color.GRAY);
                    } else {
                        maptiles[p.x + i][p.y - j].setBackground(Color.WHITE);
                    }
                }
                if (p.y + j < 20 && p.x + i < 20) {
                    if (map[p.x + i][p.y + j] == 0) {
                        maptiles[p.x + i][p.y + j].setBackground(Color.GRAY);
                    } else {
                        maptiles[p.x + i][p.y + j].setBackground(Color.WHITE);
                    }
                }
                if (p.y - j >= 0 && p.x - i >= 0) {
                    if (map[p.x - i][p.y - j] == 0) {
                        maptiles[p.x - i][p.y - j].setBackground(Color.GRAY);
                    } else {
                        maptiles[p.x - i][p.y - j].setBackground(Color.WHITE);
                    }
                }
                if (p.y + j < 20 && p.x - i >= 0) {
                    if (map[p.x - i][p.y + j] == 0) {
                        maptiles[p.x - i][p.y + j].setBackground(Color.GRAY);
                    } else {
                        maptiles[p.x - i][p.y + j].setBackground(Color.WHITE);
                    }
                }
            }
        }
        this.setVisible(true);
    }

    public void loadMap(String str) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(str));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String mapStr = sb.toString();

            int counter = 0;
            for (int y = 0; y < columns; y++) {
                for (int x = 0; x < rows; x++) {
                    String mapChar = mapStr.substring(counter, counter + 1);
                    if (!mapChar.equals("\r\n") && !mapChar.equals("\n") && !mapChar.equals("\r")) {
                        map[x][y] = Integer.parseInt(mapChar);
                    } else {
                        x--;
                        System.out.print(mapChar);
                    }
                    counter++;
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to load existing map(if exists), creating new map.");
        }
    }
    
    private boolean onList(String name, ArrayList<String> scores) {
        for(int i = 0;i < scores.size(); ++i) {
            String[] parts = scores.get(i).split(" ");
            if(parts[0].equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private int onPoints(String name, ArrayList<String> scores) {
        for(int i = 0;i < scores.size(); ++i) {
            String[] parts = scores.get(i).split(" ");
            if(parts[0].equals(name)) {
                return Integer.parseInt(parts[1]);
            }
        }
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time++;
        tlabel.setText("Time: " + time);
        
        if (random == 1 && d.y >= 0) {
            if (map[d.x][d.y - 1] != 0) {
                d.moveUp();
            } else {
                random = rand.nextInt(4) + 1;
            }
        } else if (random == 2 && d.y < 20) {
            if (map[d.x][d.y + 1] != 0) {
                d.moveDown();
            } else {
                random = rand.nextInt(4) + 1;
            }
        } else if (random == 3 && d.x < 20) {
            if (map[d.x + 1][d.y] != 0) {
                d.moveRight();
            } else {
                random = rand.nextInt(4) + 1;
            }
        } else if (random == 4 && d.x >= 0) {
            if (map[d.x - 1][d.y] != 0) {
                d.moveLeft();
            } else {
                random = rand.nextInt(4) + 1;
            }
        }
        if (maptiles[d.x][d.y].getBackground() == Color.BLACK) {
            d.setBackground(Color.BLACK);
        } else {
            d.setBackground(Color.RED);
        }
        if (p.x + 1 == d.x && p.y == d.y) {
            t.stop();
            JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            try {
                new MainMenu();
            } catch (SQLException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (p.x == d.x && p.y + 1 == d.y) {
            t.stop();
            JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            try {
                new MainMenu();
            } catch (SQLException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (p.x == d.x && p.y - 1 == d.y) {
            t.stop();
            JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            try {
                new MainMenu();
            } catch (SQLException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (p.x - 1 == d.x && p.y == d.y) {
            t.stop();
            JOptionPane.showMessageDialog(null, "Game Over!", "End Game", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            try {
                new MainMenu();
            } catch (SQLException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
