/**
 * A basic cell clas that contains all relevant information for an agent trying to get to the cell
 * Created by marten on 2016-10-05.
 */
public class Cell {
    private boolean isFound; // A cell is found if an agent has been to a neighbouring cell
    private boolean isWall; // Cant go on walls
    private double payload = 0; // A value the agent can use

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

    public void setFound(boolean found) {
        isFound = found;
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

    public void incrementPayload(){ this.payload += 1; }
}
