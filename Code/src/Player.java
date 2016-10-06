/**
 * The player interface, the player can play.
 * Created by marten on 2016-10-05.
 */
public interface Player {

    State play(State gameState);
    
    public static int heuristic(State gameState){
    	/*
    	double dist = 0;
    	for (Agent a1:gameState.agents){
    		for (Agent a2:gameState.agents){
        		if (!a1.equals(a2)){
        			dist += (a1.getX()-a2.getX())*(a1.getX()-a2.getX());
        			dist +=	(a1.getY()-a2.getY())*(a1.getY()-a2.getY());
        		}
        	}
    	}*/
    	return gameState.maze.getExplored();//+(int)(dist/300);
    }
}
