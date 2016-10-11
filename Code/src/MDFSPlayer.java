import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A player that follows the MDFS algorithm from brick and mortar
 * payload = 255 means explored
 * payload = -1 means visited
 */
public class MDFSPlayer implements Player {

    public MDFSPlayer(){

    }

    @Override
    public State play(State gameState) {
        Agent agent;
        Cell currentCell;
        for (int id = 0; id < gameState.agents.length;id++){
            agent = gameState.agents[id];
            List<int []> moves = agent.getPossibleMoves();
            ArrayList<int []> unexploredMoves = new ArrayList<>();
            ArrayList<int []> unvisitedMoves = new ArrayList<>();
            currentCell = gameState.maze.getCell(agent.getX(),agent.getY());

            for(int[] neighbour : moves){ //Add the unexplored cells to a list
                Cell cell = gameState.maze.getCell(neighbour);
                if(cell.getPayload() == 0){
                    unexploredMoves.add(neighbour);
                }
                if(cell.getPayload() != -1){ //We NEVER want to go to a
                    // visited cell
                    unvisitedMoves.add(neighbour);
                }
            }

            if(unexploredMoves.size() > 0){
                Collections.shuffle(unexploredMoves);
                int[] oldPos = {agent.getX(),agent.getY()};
                agent.move(unexploredMoves.get(0));
                currentCell = gameState.maze.getCell(agent.getX(),agent.getY());
                currentCell.setPayload(255);
                currentCell.setAgentID(id);
                currentCell.setParent(oldPos);
            }
            else{
                if(currentCell.getAgentID() == id){
                    currentCell.setPayload(-1);
                    agent.move(currentCell.getParent());
                }
                else if(!unvisitedMoves.isEmpty()){
                    Collections.shuffle(unvisitedMoves);
                    agent.move(unvisitedMoves.get(0));
                }
            }
        }

        return gameState;
    }

    @Override
    public String toString() {
        return "MDFSPlayer";
    }
}
