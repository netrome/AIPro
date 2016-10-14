import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Main class that runs the game. For now only for simple testing
 *
 * Created by marten on 2016-10-05.
 */
public class Main {
    private static boolean print = true;

    /**
     * Sets up a maze, a certain number of agents and a player
     * and runs a game with a gui
     */
    public static void main(String args[]){
    	run(3,0.5,50,1);
    	System.out.println("Ran.");

        /*
    	for (int playerType = 1; playerType<5;playerType++){
    		for (int agents=1;agents<100;agents+=10){
    			run(agents,0.5,50,playerType);
    			System.out.println("Run "+playerType+" out of 4 with "+agents+" agetns.");
    	    }
        }
    	*/
    	
    }
    
    public static void run( int numberOfAgents, double easy, int mazeSize ,int playerType) {
        /*
        Variables for keeping track of performance
         */
    	MazeGui gui = null;
        int[] seeds = {42, 1337, 137, 628};
        for(int seed:seeds) {
            long count = 0;
            long start = System.nanoTime();
            StringBuilder out = new StringBuilder();
            Maze maze = new Maze(seed);
            maze.primsMaze(mazeSize, mazeSize);
            //double easy = 0.5;
            maze.easyfy(easy);
            maze.easyfy(easy);
            Player player;
            System.out.println(playerType);
            if (playerType==0){
            	player = new GlobalPlayer();
            }
            else if(playerType==1){
            	player = new ClosestCellPlayer2();
            }
            else if (playerType==2){
            	player = new AntsPlayer();
            }
            else if (playerType==3){
            	player = new DeepAntsPlayer();
            }
            else {
            	player = new MDFSPlayer();
            }
            maze.discoverEdges();
            int[] startPos = maze.getFreePos();
            //System.out.println(maze.getCell(startPos[0], startPos[1]).isWall());

            //int numberOfAgents = 3;
            Agent[] agents = new Agent[numberOfAgents];
            for (int i = 0; i < numberOfAgents; i++)
                agents[i] = new Agent(startPos[0], startPos[1], maze);
            State state = new State(agents, maze);

            gui = new MazeGui(maze);
            while (!state.maze.isExplored()) {
                gui.updateAgentPos(agents);
                gui.repaint();
                state = player.play(state);
                agents = state.agents;
                gui.changeMaze(state.maze);
        	/*try {
        	    Thread.sleep(40);
        	} catch(InterruptedException ex) {
        	    Thread.currentThread().interrupt();
        	}*/
                gui.updateAgentPos(agents);
                gui.repaint();

            /*
             Code that saves the preformace data to a buffer
              */
                count++;
                out.append(count + ", " + (System.nanoTime() - start) + ", " + (state.maze
                        .getExploredPercent()) + "\n");

            }
        /*
        Write the performance data to file
         */
            if (print) {
                System.out.println(out);
                try {
                    System.err.println(System.getProperty("user.dir"));
                    // file names as follows: A-Algorithm_N-#agents_S-WxH_E-easy.csv
                    String name = "A-" + player.toString()
                            + "_N-" + numberOfAgents + "_S-" + maze.getWidth() + "x" + maze
                            .getHeight() + "_E-" + easy + "_SEED-"+seed+".csv";
                    Writer writer = new BufferedWriter(new OutputStreamWriter(new
                            FileOutputStream("results/" + name)));
                    writer.write(out.toString());

                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    System.err.println("we fucked up!");
                }
            }
        }

        System.err.println("Pling!");
        //Cell cell = new Cell();
        gui.setVisible(false);
    }
}
