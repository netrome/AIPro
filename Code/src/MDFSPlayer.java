import java.util.Collections;
import java.util.List;

/**
 * A player that follows the MDFS algorithm from brick and mortar
 * payload = 1 means explored
 * payload = 2 means visited
 */
public class MDFSPlayer implements Player {

    public MDFSPlayer(){

    }

    @Override
    public State play(State gameState) {
        Agent agent;
        Cell cell;
        for (int id = 0; id < gameState.agents.length;id++){
            agent = gameState.agents[id];
            List<int []> moves = agent.getPossibleMoves();

            for(int[] neighbour : moves){

            }

            agent.move(moves.get(0)[0], moves.get(0)[1]);
        }

        return gameState;
    }
}
