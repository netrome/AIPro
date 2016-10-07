/**
 * A basic cell clas that contains all relevant information for an agent trying to get to the cell
 * Created by marten on 2016-10-05.
 */
public class Cell {
    private boolean isFound; // A cell is found if an agent has been to a neighbouring cell
    private boolean isWall; // Cant go on walls
    private int agentID; //Index of the agent that "owns" this cell
    private int[] parent; //points in the direction of the owner cell
    private double payload; // A value the agent can use

    /**
     * Constructs an empty cell
     */
    public Cell(){
        isFound = false;
        isWall = false;
        payload = 0;
    }
    
    public Cell(boolean found, boolean wall, double payloadIn){
        isFound = found;
        isWall = wall;
        payload = payloadIn;
    }
    
    /**
     * returns a clone of the cell
     */
    public Cell clone(){
    	return new Cell(isFound,isWall,payload);
    }

    /**
     * Creates a cell with information regarding if it is a wall
     */
    public Cell(boolean isWall){
        this.isWall = isWall;
    }

    public int getAgentID() {
        return agentID;
    }

    public int[] getParent() {
        return parent;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public void setParent(int[] parent) {
        this.parent = parent;
    }

    public boolean isFound() {
        return isFound;
    }

    public boolean isWall() {
        return isWall;
    }

    public double getPayload() {
        return payload;
    }

    public void setPayload(double payload) {
        this.payload = payload;
    }
}
