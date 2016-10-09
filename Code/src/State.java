import java.util.*;

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

		for (Agent a : agents){
			maze.getCell(a.getX(), a.getY()).setFound(true);
			for (int[] coord : maze.getNeighbours(a.getX(), a.getY())){
				maze.getCell(coord[0], coord[1]).setFound(true);
			}
		}

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

    public String toString(){

		Map<Cell, Integer> agentPositions = new HashMap<>();

		for (Agent a : agents){
			Cell c = maze.getCell(a.getX(), a.getY());
			if(agentPositions.containsKey(c)){
				agentPositions.put(c, agentPositions.get(c) + 1);
			}else {
				agentPositions.put(c, 1);
			}
		}

		StringBuilder builder = new StringBuilder();

		for (int x = 0; x < maze.getMaze().length; x++) {
			for (int y = 0; y < maze.getMaze()[0].length; y++) {

				Cell c = maze.getCell(x, y);

				if(!c.isFound()){
					builder.append("?");
				}
				else if(c.isWall()){
					builder.append("#");
				}
				else if(agentPositions.containsKey(c)){
					builder.append(agentPositions.get(c));
				}
				else{
					builder.append(" ");
				}

				builder.append(" ");
			}
			builder.append("\n");
		}

		return builder.toString();
	}
     
}
