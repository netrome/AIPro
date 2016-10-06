import java.util.List;
import java.util.ArrayList;

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
    
    /**
     * returns all possible other states reachable from the current state
     */
    public List<State> findPossibleMoves(){
    	List<State> retStates = new ArrayList<State>();;
    	List<State> tempStates = new ArrayList<State>();
    	tempStates.add(this.clone());
    	for (int a =0; a< agents.length; a++){
    		retStates = new ArrayList<State>();
    		//System.out.println("Size of temp state: "+tempStates.size());
    		//System.out.println("Size of moves: "+agents[a].getPossibleMoves().size());
    		for (State state : tempStates){
    			for (int[] pos: agents[a].getPossibleMoves()){
    				State newState = state.clone();
    				newState.agents[a].move(pos[0], pos[1]);
    				retStates.add(newState);
    			}
    		}
    		tempStates = retStates;
    	}
    	/*
    	MazeGui gui = new MazeGui(retStates.get(0).maze);
    	for (State s:retStates){
    		gui.changeMaze(s.maze);
    		gui.updateAgentPos(agents);
        	gui.repaint();
    		try {
        	    Thread.sleep(500);
        	} catch(InterruptedException ex) {
        	    Thread.currentThread().interrupt();
        	}
    	}
    	*/
		return retStates;
    }
    /**
     * returns if the state is complete
     */
    public boolean isEOG(){
    	return maze.isExplored();
    }
    
    /**
     * returns a clone of the state
     */
    public State clone(){
    	Maze retMaze = maze.clone();
    	Agent[] retAgents = new Agent[agents.length];
    	for (int i=0; i<agents.length;i++){
    		retAgents[i]=agents[i].clone(retMaze);
    	}
    	return new State(retAgents,retMaze);
    }
     
}
