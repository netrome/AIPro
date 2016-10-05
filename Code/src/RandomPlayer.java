import java.util.Collections;
import java.util.List;

/**
 * The random player only makes random moves
 * Created by marten on 2016-10-05.
 */
public class RandomPlayer implements Player {

    public RandomPlayer(){

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
