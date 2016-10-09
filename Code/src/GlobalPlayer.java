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
        List<State> path;

        // Search for new path if new cells were explored
        if(previousPath == null || previousPath.size() == 1 ||
                previousPath.get(0).maze.getExplored() != state.maze.getExplored()){
            path = AStar.search(state, new GlobalSuccessor(state), new GlobalHeuristic(), Integer.MAX_VALUE);
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

        previousPath = path;

        return path.get(1);
    }


    private class GlobalSuccessor implements AStar.Successor{

        private State start;

        GlobalSuccessor(State start){
            this.start = start;
        }

        public List<State> successors(State s){

            List<Integer> fixedAgents = new ArrayList<>();

            for (int a = 0; a < s.agents.length; a++){
                if (agentOnBoarder(s, s.agents[a])){
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
                if (!agentOnBoarder(s, a)){
                    return false;
                }
            }

            return true;

            //return s.isEOG();
        }

        private boolean agentOnBoarder(State s, Agent a){
            for (int[] coord : s.maze.getNeighbours(a.getX(), a.getY())){
                if (!start.maze.getCell(coord[0], coord[1]).isFound()){
                    return true;
                }
            }
            return false;
        }

    }

    private class GlobalHeuristic implements AStar.Heuristic{

        public double distance(State s1, State s2){
            return 1 + (s1.maze.getExplored() - s2.maze.getExplored());
        }


        public double costToGoal(State s){

            return 0;
            //return s.maze.getHeight() * s.maze.getWidth() - s.maze.getExplored();

        }


    }

}
