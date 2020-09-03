package player;

import java.awt.Color;

import javax.swing.JPanel;
import labyrinth.Maze;

public class Player extends JPanel {

    public int x, y;
    private final String name;
    private int points;

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
        this.setBackground(Color.getHSBColor(0.3f, 0.3f, 1));
        this.setSize(Maze.panelSize, Maze.panelSize);
    }

    public void moveLeft() {
        if (x > 0 && Maze.map[x - 1][y] == 1) {
            this.setLocation(this.getX() - 25, this.getY());
            x--;
        }
    }

    public void moveRight() {
        if (x < Maze.columns - 1 && Maze.map[x + 1][y] == 1) {
            this.setLocation(this.getX() + 25, this.getY());
            x++;
        }
    }

    public void moveUp() {
        if (y > 0 && Maze.map[x][y - 1] == 1) {
            this.setLocation(this.getX(), this.getY() - 25);
            y--;
        }
    }

    public void moveDown() {
        if (y < Maze.rows - 1 && Maze.map[x][y + 1] == 1) {
            this.setLocation(this.getX(), this.getY() + 25);
            y++;
        }
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
