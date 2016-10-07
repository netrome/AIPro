import java.util.List;

/**
 * The ants player moves in the direction of the lowest payload.
 * Before moving the ant releases a feromone which increases the payload of the current cell.
 * Edit-the ant releases the feromone onto the cell it is heading towards
 */
public class AntsPlayer implements Player {

    public AntsPlayer(){

    }

    @Override
    public State play(State gameState) {
        for (Agent agent : gameState.agents){

            // Get moves
            List<int []> moves = agent.getPossibleMoves();

            // Get lowest feromone move
            int [] bestmove = {agent.getX(), agent.getY()};
            double minPayload = gameState.maze.getCell(bestmove).getPayload();
            for (int [] move : moves){
                double payload = gameState.maze.getCell(move).getPayload();
                if (payload < minPayload){
                    bestmove = move;
                    minPayload = payload;
                }
            }

            // Release feromone and move
            gameState.maze.getCell(bestmove).incrementPayload();
            agent.move(bestmove);
        }

        return gameState;
    }
}
