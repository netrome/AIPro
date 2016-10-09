import java.util.Collections;
import java.util.List;

/**
 * The player only makes random moves
 */
public class GlobalPlayer implements Player {

    private State goal;


    public GlobalPlayer(){

    }

    @Override
    public State play(State state) {


        List<State> path = AStar.search(state, new GlobalSuccessor(), new GlobalHeuristic());

        if(path.size() == 0){
            System.err.println("No path!");
            return state;
        }
        else if(path.size() == 1){
            System.err.println("Reached goal!");
            return state;
        }

        return path.get(1);
    }


    private class GlobalSuccessor implements AStar.Successor{

        public List<State> successors(State s){
            return s.findPossibleMoves();
        }

        public boolean isGoal(State s){
            return s.isEOG();
        }

    }

    private class GlobalHeuristic implements AStar.Heuristic{

        public double distance(State s1, State s2){
            return 1 + (s1.maze.getExplored() - s2.maze.getExplored());
        }


        public double costToGoal(State s){
            return 0;
        }

    }

}
