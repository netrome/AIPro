import java.util.Collections;
import java.util.List;

/**
 * The deep ants player is an ants player with the ability too look forward a number of steps
 * ...not implemented yet
 *
 * The ants player moves in the direction of the lowest payload.
 * Before moving the ant releases a feromone which increases the payload of the current cell.
 * Edit-the ant releases the feromone onto the cell it is heading towards
 */
public class DeepAntsPlayer implements Player {
    private int depth = 5;

    public DeepAntsPlayer() {

    }

    public DeepAntsPlayer(int depth) {
        this.depth = depth;
    }

    @Override
    public State play(State gameState) {
        for (Agent agent : gameState.agents){
            doBestMove(agent, gameState);
        }

        return gameState;
    }

    /**
     * Moves the agent according to what is determined as the best move
     * @param agent
     * @return
     */
    private void doBestMove(Agent agent, State gameState){
        int [] pos = {agent.getX(), agent.getY()};
        int [] bestMove = pos.clone();
        double val = Double.MAX_VALUE;

        // Get next moves
        List<int []> nextMoves = agent.getPossibleMoves();

        // Non deterministic
        Collections.shuffle(nextMoves);

        // Find best move
        double tempVal;
        for ( int [] move: nextMoves){
            tempVal = minDFSmove(agent, gameState, move, depth);
            if (tempVal < val){
                val = tempVal;
                bestMove = move;
            }
        }


        // Release feromone and move
        gameState.maze.getCell(bestMove).incrementPayload(10);
        agent.move(bestMove);

        // Release feromone to neighbours too
        nextMoves = gameState.maze.getPossibleMoves(bestMove);
        for (int [] move : nextMoves){
            gameState.maze.getCell(move).incrementPayload();
        }
    }

    /**
     * Returns the minimum sum of encountered feromones for a move in this direction.
     */
    private double minDFSmove(Agent agent, State gameState, int[] pos, int depth){
        double val = Double.MAX_VALUE;
        Cell cell = gameState.maze.getCell(pos);

        // If depth is zero return the feromone level
        if (depth == 0){
            return cell.getPayload();
        }

        // Get next moves
        List<int []> nextMoves = gameState.maze.getPossibleMoves(pos);

        // Non deterministic
        Collections.shuffle(nextMoves);

        // Get lowest feromone move
        double tempVal;
        for (int [] move: nextMoves){
            tempVal = minDFSmove(agent, gameState, move, depth - 1);
            if (tempVal < val){
                val = tempVal;
            }
        }

        // Add feromone level and returm
        return val + cell.getPayload();
    }

    @Override
    public String toString() {
        return "DeepAntsPlayer" + depth;
    }
}
