import java.util.List;

/**
 * The ants player moves in the direction of the lowest payload.
 * Before moving the ant releases a feromone which increases the payload of the current cell.
 */
public class AntsPlayer implements Player {

    public AntsPlayer(){

    }

    @Override
    public State play(State gameState) {
        for (Agent agent : gameState.agents){

            // Release feromone
            Cell cell = gameState.maze.getCell(agent.getX(), agent.getY());
            cell.setPayload(cell.getPayload() + 1);

            // Get moves
            List<int []> moves = agent.getPossibleMoves();

            // Get lowest feromone move
            int [] bestmove = {agent.getX(), agent.getY()};
            double maxPayload = gameState.maze.getCell(bestmove).getPayload();
            for (int [] move : moves){
                double payload = gameState.maze.getCell(move).getPayload();
                if (payload > maxPayload){
                    bestmove = move;
                    maxPayload = payload;
                }
            }

            agent.move(bestmove);
        }

        return gameState;
    }
}
