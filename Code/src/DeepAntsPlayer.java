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

    public DeepAntsPlayer() {

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
        int depth = 5;

        // Get next moves
        List<int []> nextMoves = agent.getPossibleMoves();

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
        gameState.maze.getCell(bestMove).incrementPayload();
        agent.move(bestMove);
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
        List<int []> nextMoves = agent.getPossibleMoves(pos);

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
}
