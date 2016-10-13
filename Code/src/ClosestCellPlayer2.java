import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * The random player only makes random moves
 * Created by marten on 2016-10-05.
 */
public class ClosestCellPlayer2 implements Player {
	
	private List<Node> rootNodes = new ArrayList<Node>();
	private int [][] agentsNextPos;
	
    public ClosestCellPlayer2(){

    }

    @Override
    public State play(State gameState) {
    	
    	agentsNextPos = new int[gameState.agents.length][2];
    	gameState.maze.setAllPayload(Double.MAX_VALUE);
    	gameState.maze.setAllOwners(-1);
    	for (int i=0; i < gameState.agents.length;i++){
    		agentsNextPos[i]=closestUnexplored(i,gameState);
    	}
    	for (int i=0; i < gameState.agents.length;i++){
    		gameState.agents[i].move(agentsNextPos[i]);
    	}
		return gameState;
    }
    
    public int[] closestUnexplored(int index,State gameState){
    	int x = gameState.agents[index].getX();
    	int y = gameState.agents[index].getY();
    	
    	int count=0;
    	List <String> visited = new ArrayList<String>();
    	visited.add(x+" "+y);
    	List <Node> expNode= new ArrayList<Node>();
    	for (int[] posChild : gameState.maze.getPossibleMovesV2(x,y)){
    		expNode.add(new Node(posChild));
    	}
    	List<Node> tempNodes = new ArrayList<Node>();
    	while (expNode.size()>0){
    		for (Node posNode:expNode){
        		if (!gameState.maze.getCell(posNode.pos).isFound()
        				&& gameState.maze.getCell(posNode.pos).getPayload()>count){
        			if (gameState.maze.getCell(posNode.pos).getPayload()== Double.MAX_VALUE){
        				gameState.maze.getCell(posNode.pos).setPayload(count);
        				gameState.maze.getCell(posNode.pos).setAgentID(index);
        				return posNode.getRoot().pos;
        			}
        			/*else{
        				System.out.println("count: "+count+ ". Payload: "+gameState.maze.getCell(posNode.pos).getPayload());
        				int otherIndex = gameState.maze.getCell(posNode.pos).getAgentID();
        				gameState.maze.getCell(posNode.pos).setPayload(count);
        				gameState.maze.getCell(posNode.pos).setAgentID(index);
        				
        				agentsNextPos[otherIndex]= closestUnexplored(otherIndex, gameState);
        			}*/
        		}
        		for (int[] posChild : gameState.maze.getPossibleMovesV2(posNode.pos)){
        			if (!visited.contains(posChild[0]+" "+posChild[1])){
        				visited.add(posChild[0]+" "+posChild[1]);
        				tempNodes.add(posNode.makeChild(posChild));
        			}
        		}
    		}
    		expNode = tempNodes;
    		tempNodes = new ArrayList<Node>();
        	count++;
    	}
    	
    	//return new int[]{x,y};
    	gameState.maze.setAllPayload(Double.MAX_VALUE);
    	gameState.maze.setAllOwners(-1);
    	return closestUnexplored(index,gameState);
    }

    @Override
    public String toString() {
        return "ClosestPlayer";
    }
    
    private class Node{
    	public Node parent;
    	public int[] pos;
    	public List<Node> child = new ArrayList<Node>();
    	
    	public Node(int[] pos){
    		this.pos = pos;
    		parent = null;
    	}
    	public Node(int[] pos, Node parent){
    		this.pos = pos;
    		this.parent = parent;
    	}
    	public Node makeChild(int[] posIn){
    		Node newNode = new Node(posIn,this);
    		child.add(newNode);
    		return newNode;
    	}
    	public Node getRoot(){
    		if (parent==null){
    			return this;
    		}
    		return parent.getRoot();
    	}
    }
}
