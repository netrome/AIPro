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
    public static void main(String[] args) {
        /*
        Variables for keeping track of performance
         */
        int[] seeds = {42,1337,137,628};
        for(int seed:seeds) {
            long count = 0;
            long start = System.nanoTime();
            StringBuilder out = new StringBuilder();
            Maze maze = new Maze(seed);
            maze.primsMaze(50, 50);
            double easy = 1;
            maze.easyfy(easy);
            Player player = new ClosestCellPlayer2();
            maze.discoverEdges();
            int[] startPos = maze.getFreePos();
            //System.out.println(maze.getCell(startPos[0], startPos[1]).isWall());

            int numberOfAgents = 3;
            Agent[] agents = new Agent[numberOfAgents];
            for (int i = 0; i < numberOfAgents; i++)
                agents[i] = new Agent(startPos[0], startPos[1], maze);
            State state = new State(agents, maze);

            MazeGui gui = new MazeGui(maze);
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
    }
}
