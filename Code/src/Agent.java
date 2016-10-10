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
        this.maze=maze;
    }
    
    /**
     * returns a clone of the agent belonging to the stated maze.
     */
    public Agent clone(Maze maze){
    	return new Agent(xpos,ypos,maze);
    }

    /**
     * Get the possible moves for this agent
     */
    public List<int[]> getPossibleMoves(){
        return getPossibleMoves(xpos, ypos);
    }

    public List<int[]> getPossibleMoves(int[] pos){
        return getPossibleMoves(pos[0], pos[1]);
    }

    /**
     * Get the possible moves for this agent if it were somewhere else
     */
    public List<int[]> getPossibleMoves(int xpos, int ypos){
        List<int []> neighbours = maze.getNeighbours(xpos, ypos);
        List<int []> moves = new ArrayList<>();

        for (int[] cord : neighbours){
            Cell cell = maze.getCell(cord[0], cord[1]);
            if (!cell.isWall()){
                moves.add(cord);
            }
        }

        return moves;
    }

    /**
     * Moves the agent and discovers new cell.
     * @param x
     * @param y
     */
    public void move(int x, int y){
        xpos = x;
        ypos = y;
        maze.discover(x, y);
    }

    public void move(int[] pos){
        move(pos[0], pos[1]);
    }
    
    public int getX(){
    	return xpos;
    }
    
    public int getY(){
    	return ypos;
    }

}
