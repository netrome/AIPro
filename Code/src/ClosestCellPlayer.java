import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

/**
 * The random player only makes random moves
 * Created by marten on 2016-10-05.
 */
public class ClosestCellPlayer implements Player {
	
	private List<Node> rootNodes = new ArrayList<Node>();
	private Hashtable <String,Integer> agentGoals = new Hashtable <String,Integer>();
	private int [][] agentsNextPos;
	
    public ClosestCellPlayer(){

    }

    @Override
    public State play(State gameState) {
    	agentsNextPos = new int[gameState.agents.length][2];
    	rootNodes = new ArrayList<Node>();
    	agentGoals = new Hashtable <String,Integer>();
    	gameState.maze.setAllPayload(Double.MIN_VALUE);
    	
    	for (int i=0; i < gameState.agents.length;i++){
    		move(gameState,i);
    	}
    	for (int i=0; i < gameState.agents.length;i++){
    		gameState.agents[i].move(agentsNextPos[i]);
    	}

        return gameState;
    }
    
    public void move(State gameState,int agentIndex){
    	System.out.println(agentGoals.size());
    	int [][] metaData = closestUnexplored(agentIndex,gameState);
    	if (!(metaData == null)){
    	agentsNextPos[agentIndex]=metaData[1];
    	if (metaData[2][0]==1 && agentGoals.containsKey(metaData[0][0]+" "+metaData[0][1])){
    		System.out.println("After Collision");
    		int tempPlayerIndex = agentGoals.get(metaData[0][0]+" "+metaData[0][1]);
    		agentGoals.put(metaData[0][0]+" "+metaData[0][1], agentIndex);
    		move(gameState,tempPlayerIndex);
    	}
    	agentGoals.put(metaData[0][0]+" "+metaData[0][1], agentIndex);
    	}
    	else{
    		System.out.println("Null Value");
    		//agentsNextPos[agentIndex]=new int[]{gameState.agents[agentIndex].getX(),
    		//		gameState.agents[agentIndex].getY()};
    	}
    }
    
    public int[][] closestUnexplored(int i,State gameState){
    	int x = gameState.agents[i].getX();
    	int y = gameState.agents[i].getY();
    	if (gameState.maze.isExplored()){
    		return null;
    	}
    	rootNodes = new ArrayList<Node>();
    	int count=0;
    	List <String> visited = new ArrayList<String>();
    	visited.add(x+" "+y);
    	List <Node> expNode= new ArrayList<Node>();
    	for (int[] posChild : gameState.maze.getPossibleMovesV2(x,y)){
    		expNode.add(new Node(posChild));
    	}
    	rootNodes=expNode;
    	List<Node> tempNodes = new ArrayList<Node>();
    	while (expNode.size()>0){
    		//System.out.println("Exp size "+expNode.size());
    	for (Node posNode:expNode){
    		if (!gameState.maze.getCell((int[])posNode.getValue()).isFound()
    				&& gameState.maze.getCell((int[])posNode.getValue()).getPayload()<count){
    			if (gameState.maze.getCell((int[])posNode.getValue()).getPayload()== Double.MIN_VALUE){
    				gameState.maze.getCell((int[])posNode.getValue()).setPayload(count);
    				return new int[][]{(int[])posNode.getValue(),(int[])posNode.getRoot().value,{0}};
    			}else{
    				gameState.maze.getCell((int[])posNode.getValue()).setPayload(count);
    				System.out.println("COLLISION!!");
    				return new int[][]{(int[])posNode.getValue(),(int[])posNode.getRoot().value,{1}};
    			}
    		}
    		for (int[] posChild : gameState.maze.getPossibleMovesV2((int[])posNode.getValue())){
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
    	return null;
    }
    
    public int getHorisonNum(){
    	int i = 0;
    	for(Node n: rootNodes){
    		if (((int[])n.getValue())[0]>0 && ((int[])n.getValue())[1]>0){
    			i+=n.getNumLeaf();
    		}
    	}
    	return i;
    }

    @Override
    public String toString() {
        return "ClosestPlayer";
    }
    private class Node{
    	public Node parent;
    	public Object value;
    	public List<Node> child = new ArrayList<Node>();
    	public boolean leaf = false;
    	
    	public Node(Object value){
    		this.value = value;
    		parent = null;
    	}
    	public Node(Object value, Node parent){
    		this.value = value;
    		this.parent = parent;
    		
    	}
    	
    	public Node makeChild(Object valueIn){
    		Node newNode = new Node(valueIn,this);
    		child.add(newNode);
    		return newNode;
    	}
    	
    	public Object getValue(){
    		return value;
    	}
    	public Node getRoot(){
    		if (parent==null){
    			return this;
    		}
    		return parent.getRoot();
    	}
    	public int getNumLeaf(){
    		if (child.size()==0){
    			return 1;
    		}
    		int ret=0;
    		for (Node n:child){
    			ret+=n.getNumLeaf();
    		}
    		return ret;
    	}
    }
}
