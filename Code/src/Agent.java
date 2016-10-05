import java.util.List;

/**
 * The class that holds the position of an agent on a maze, as well as gets the possible moves for this position
 * Created by marten on 2016-10-05.
 */
public class Agent {
    private Maze maze;
    private int xpos;
    private int ypos;

    public Agent(int x, int y){
        xpos = x;
        ypos = y;
    }

    /**
     * Get the possible moves for this agent
     */
    public List<int[]> getPossibleMoves(Maze maze){
        return maze.getNeighbours(xpos, ypos);
    }

    public void move(int x, int y, Maze maze){
        return maze.move(xpos, ypos);
    }

}
