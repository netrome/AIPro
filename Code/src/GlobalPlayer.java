import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The player only makes random moves
 */
public class GlobalPlayer implements Player {

    private State goal;

    private List<State> previousPath = null;

    private int count = 0;

    public GlobalPlayer(){

    }

    @Override
    public State play(State state) {
        Maze m = state.maze;

        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                m.getCell(x, y).setPayload(distanceToUndiscoveredCell(m, x, y));
            }
        }


        List<State> path;

        // Search for new path if new cells were explored
        if(previousPath == null || previousPath.size() == 1 ||
                previousPath.get(0).maze.getExplored() != state.maze.getExplored()){
            path = AStar.search(state, new GlobalSuccessor(state), new GlobalHeuristic(state), Integer.MAX_VALUE);
        }
        // Otherwise use old path
        else{
            path = previousPath;
            path.remove(0);
        }

        if(path.size() == 0){
            System.err.println("No path!");
            return state;
        }
        // Found goal
        else if(path.size() == 1){
            return state;
        }

        previousPath = path;

        return path.get(1);
    }

    private double distanceToUndiscoveredCell(Maze maze, int xPos, int yPos){

        double closest = Double.MAX_VALUE;

        for (int x = 0; x < maze.getWidth(); x++){
            for (int y = 0; y < maze.getHeight(); y++){

                Cell c = maze.getCell(x, y);

                if(!c.isFound()){

                    double d = Math.pow(x - xPos, 2) + Math.pow(y - yPos, 2);

                    if(d < closest){
                        closest = d;
                    }
                }

            }

        }

        return closest;
    }


    private class GlobalSuccessor implements AStar.Successor{

        private State start;

        GlobalSuccessor(State start){
            this.start = start;
        }

        public List<State> successors(State s){

            List<Integer> fixedAgents = new ArrayList<>();

            for (int a = 0; a < s.agents.length; a++){
                if (agentOnBoarder(start.maze, s.agents[a])){
                    fixedAgents.add(a);
                }
            }

            return s.findPossibleMoves(fixedAgents);

        }

        public boolean isGoal(State s){

            if (s.isEOG()){
                return true;
            }

            for (Agent a : s.agents){
                if (!agentOnBoarder(start.maze, a)){
                    return false;
                }
            }

            return true;

            //return s.isEOG();
        }

        private boolean agentOnBoarder(Maze m, Agent a){

            for (int[] coord: m.getNeighbours(a.getX(), a.getY())){

                if(!m.getCell(coord[0], coord[1]).isFound()){
                    return true;
                }

            }

            return false;
        }

    }

    private class GlobalHeuristic implements AStar.Heuristic{
        private State start;

        GlobalHeuristic(State start){
            this.start = start;
        }


        public double distance(State s1, State s2){
            return 10 + (s1.maze.getExplored() - s2.maze.getExplored());
        }

        public double costToGoal(State s){

            double sum = 0;
            for (Agent a : s.agents){
                double d = distanceToUndiscoveredCell(start.maze, a.getX(), a.getY());
                if(d==1){
                    sum -= 100000;
                }else{
                    sum += d;
                }
                //sum += d;
            }
            return sum;


            //return 0;

            /*
            List<Double> closest = new ArrayList<>();

            for (int i = 0; i < s.agents.length; i++){
                closest.add(Double.MAX_VALUE);
            }


            for (int x = 0; x < s.maze.getWidth(); x++){
                for (int y = 0; y < s.maze.getHeight(); y++){

                    Cell c = s.maze.getCell(x, y);

                    if(!c.isFound()){

                        for (int i = 0; i < closest.size(); i++){
                            double d = Math.pow(x - s.agents[i].getX(), 2) + Math.pow(y - s.agents[i].getY(), 2);

                            if(d < closest.get(i)){
                                closest.set(i, d);
                            }

                        }


                    }

                }

            }

            double sum = 0;

            for (Double d : closest){
                sum += Math.pow(d, 3);
            }

            return sum;*/
            //return s.maze.getHeight() * s.maze.getWidth() - s.maze.getExplored();

        }


    }

}
