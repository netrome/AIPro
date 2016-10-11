/**
 * Main class that runs the game. For now only for simple testing
 *
 * Created by marten on 2016-10-05.
 */
public class Main {
/**
 * Sets up a maze, a certain number of agents and a player
 * and runs a game with a gui
 */
    public static void main(String[] args) {
        Maze maze = new Maze();
        maze.primsMaze(125, 125);
        maze.easyfy(0.7);
        Player player = new DeepAntsPlayer();
        int[] startPos = maze.getFreePos();
        //System.out.println(maze.getCell(startPos[0], startPos[1]).isWall());

        int numberOfAgents = 10;
        Agent[] agents = new Agent[numberOfAgents];
        for (int i=0;i<numberOfAgents;i++)agents[i]=new Agent(startPos[0],startPos[1],maze);
        State state = new State(agents,maze);

        MazeGui gui = new MazeGui(maze);
        while(!state.maze.isExplored()){
        	state = player.play(state);
        	agents=state.agents;
        	gui.changeMaze(state.maze);
        	try {
        	    Thread.sleep(40);
        	} catch(InterruptedException ex) {
        	    Thread.currentThread().interrupt();
        	}
        	gui.updateAgentPos(agents);
        	gui.repaint();
        }
        System.err.println("Pling!");
        //Cell cell = new Cell();
    }
}
