package player;

import java.awt.Color;
import labyrinth.Maze;

public class Dragon extends Player {

    public Dragon() {
        super("Daragon", 0);
        this.setBackground(Color.BLACK);
        this.setSize(Maze.panelSize, Maze.panelSize);
    }
}
