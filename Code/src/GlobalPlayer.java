import java.util.Collections;
import java.util.List;

/**
 * The player only makes random moves
 */
public class GlobalPlayer implements Player {

    public GlobalPlayer(){

    }

    @Override
    public State play(State gameState) {
        for (Agent agent : gameState.agents){
            List<int []> moves = agent.getPossibleMoves();
            Collections.shuffle(moves);
            agent.move(moves.get(0)[0], moves.get(0)[1]);
        }

        return gameState;
    }
}
