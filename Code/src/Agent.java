import java.util.ArrayList;
import java.util.List;

/**
 * The class that holds the position of an agent on a maze, as well as gets the possible moves for this position
 * Created by marten on 2016-10-05.
 */
public class Agent {
    private Maze maze;
    private int xpos;
    private int ypos;

    public Agent(int x, int y, Maze maze){
        xpos = x;
        ypos = y;
    }

    /**
     * Get the possible moves for this agent
     */
    public List<int[]> getPossibleMoves(){
        List neighbours = maze.getNeighbours(xpos, ypos);
        List<int []> moves = new ArrayList<>();

        for (int[] cord : neighbours){
            if (!maze.getCell(cord[0], cord[1]).isWall()){
                moves.add(cord);
            }
        }

        return moves
    }

    public void move(int x, int y){
        xpos = x;
        ypos = y;
        return maze.move(xpos, ypos);
    }

}
