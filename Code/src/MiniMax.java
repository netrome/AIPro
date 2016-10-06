import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Authors: Axel Demborg and Elon SÃ¥ndberg
 */
public class MiniMax {
    @SuppressWarnings("unused")
	public int alphaBeta(State state, int depth, int alpha, int beta, int
            player, int ourMark)
            throws Exception{
        // state: the current state we are analyzing
        // Î±: the current best value achievable by A
        // Î²: the current best value acheivable by B
        // player: the current player
        // returns the minimax value of the state

        //System.err.println("Size of visited:" + visited.size());

        //System.err.println("Time untill deadline: "+ deadline.timeUntil());

        int v;
        List<State> nextStates = new ArrayList<State>();
        IndexValue[] nextSortedStates;


        if(depth == 0 || state.isEOG()){
            //terminal state
            return Player.heuristic(state);
        }
        else{
        	nextStates = state.findPossibleMoves();
            nextSortedStates  = range(nextStates.size());

            for(int i = 0;i< nextStates.size();i++){
                nextSortedStates[i].value = Player.heuristic(nextStates.get(i));
            }
            Arrays.sort(nextSortedStates);
        }
        //Kommer vi vilja utöka detta till att ha flera spelare?
        //dvs vi vill väll bara ha max?
        if(player == ourMark || true){
            v = Integer.MIN_VALUE;
            for(IndexValue next:nextSortedStates){
                //System.err.println("id: " + id);
                    //visited.add(id);
                    v = Integer.max(v, alphaBeta(nextStates.get(next.index), depth
                            - 1,
                            alpha, beta,
                            -(player - 3), ourMark));
                    alpha = Integer.max(alpha, v);
                    if (beta <= alpha) break;

            }
        }
        else{
            v = Integer.MAX_VALUE;
            for(IndexValue next:nextSortedStates){
                //System.err.println("id: " + id);
                    //visited.add(id);
                    v = Integer.min(v, alphaBeta(nextStates.get(next.index), depth - 1, alpha, beta,
                            -(player - 3), ourMark));
                    beta = Integer.min(beta, v);
                    if (beta <= alpha) break;

            }
        }

        return v;
    }

    public int alphaBeta(State state,int depth, int ourMark
            ) throws Exception{
        return alphaBeta(state, depth, Integer.MIN_VALUE,Integer.MAX_VALUE,-
                (ourMark-3),ourMark);
    }

    public State ids(State state,int depth){
    	int ourMark=1;
        List<State> nextStates = state.findPossibleMoves();

        IndexValue[] states = range(nextStates.size());

        int i = 0;
        try {
            int alpha;
            int v;
            for (i = 0; i <= depth; i++) {
                //System.err.println("-------------");
                alpha = Integer.MIN_VALUE;
                for (int j = 0; j < nextStates.size(); j++) {
                    v = alphaBeta(nextStates.get(states[j].index), i,alpha,
                            Integer.MAX_VALUE,-(ourMark-3), ourMark);
                    states[j].value = v;
                    alpha = Integer.max(alpha,v);
                }
                //System.err.println("");
                Arrays.sort(states);
            }
            System.err.println("Got to depth: "+depth+"!");
        }catch(Exception e){
            //System.err.println(e.getMessage());
            System.err.println("Depth: "+(i-1));
        }

        return nextStates.get(states[0].index);
    }

    private class IndexValue implements Comparable{
        public int index;
        public int value;

        public IndexValue(int indexIn){
            index = indexIn;
            value = 0;
        }

        //sorts in acending order
        public int compareTo(Object other){
            if(other instanceof IndexValue){
                return ((IndexValue)other).value -value;
            }
            return 0;
        }
    }

    private IndexValue[] range(int max){
        IndexValue[] out = new IndexValue[max];
        for(int i = 0;i < max;i++){
            out[i] = new IndexValue(i);
        }
        return out;
    }
}
