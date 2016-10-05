/**
 * The state of the game. It contains the array of agents and the maze
 * Created by marten on 2016-10-05.
 */
public class State {
    protected Agent[] agents;
    protected Maze maze;

    public State(Agent[] agents, Maze maze){
        this.agents = agents;
        this.maze = maze;
    }
}
