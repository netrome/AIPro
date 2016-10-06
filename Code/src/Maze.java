import java.util.*;

/**
 * The class that holds all the cells, and functions to get them
 */
public class Maze {

    /* Intrinsic variables */
    // Store cells
    private Cell[][] mazeData;

    /* Parameters for generationn */
    // Wall probability
    private static double WALL_PROB = 0.7;

    /**
     * Constructor
      */
	public Maze(){
	}
	
	public Maze(Cell[][] mazeData){
		this.setMaze(mazeData);
	}
	
	/**
     * returns a clone of the maze
     */
	public Maze clone(){
		Maze retMaze = new Maze();
		Cell[][] retData = new Cell[getWidth()][getHeight()];
		for (int x = 0; x <getWidth() ; x++){
			for (int y = 0; y < getHeight(); y++){
				retData[x][y] = getCell(x,y).clone();
			}}
		retMaze.setMaze(retData);
		return retMaze;
	}

    /**
     * Getters
     */
    public int getWidth(){
        return mazeData.length;
    }

    public int getHeight(){
        return mazeData[0].length;
    }

    public Cell getCell(int x, int y){
        return mazeData[x][y];
    }
	public Cell[][] getMaze() {
		return mazeData;
	}

	public void setMaze(Cell[][] mazeData) {
		this.mazeData = mazeData;
	}

    /**
     * Returns a list of neighbouring cells.
     */
    public List<int[]> getNeighbours(int x, int y){
        List<int[]> neighbourList = new ArrayList<int[]>();
     // Vi fick bara diagonal rutorna n�r det var +=2.
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(x + i > 0 && x + i < getWidth() - 1 && y + j > 0 && y + j < getHeight() - 1){
                	if (i!=0 ^ j!=0 ){
                    neighbourList.add(new int[]{x + i, y + j});
                	}
                }
            }
        }

        return neighbourList;
    }
    

    /**
     * Called when an agent is moved to a cell.
     * All neighbouring cells is found and payload is set for the current cell.
     */
    public void discover(int x, int y){
    	getCell(x, y).setFound(true);
        List<int[]> neigbours = getNeighbours(x, y);
        for (int[] cord : neigbours){
            Cell cell = getCell(cord[0], cord[1]);
            cell.setFound(true);
        }

        getCell(x, y).setPayload(1); //Magic number

    }

    /**
     * Maze creation methods
     *
     *
     *
     *
     *
     */
    public void randomMaze(int width,int height){
		Cell[][] newMazeData = new Cell[width][height];

		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){

				if (Math.random() > WALL_PROB){ // Cell is wall
					newMazeData[x][y] = new Cell(true);
				}
                else{                           // Cell is empty
                    newMazeData[x][y]= new Cell(false);
                }
            }
        }

		setMaze(newMazeData);
	}

    /**
     * generates a maze using prim's algorithm
     */
	public void primsMaze(int width,int height){
        
		Cell[][] newMazeData = new Cell[width][height];

        // Initialize new maze data as walls
		for (int x = 0;x<width;x++){
			for (int y = 0;y<height;y++){
				newMazeData[x][y] = new Cell(true);
            }
        }

        
		LinkedList <int[]> wallList = new LinkedList<int[]>();

		int x=(int) Math.round(1+Math.random()*(width-2));
		int y=(int) Math.round(1+Math.random()*(height-2));

		int xs = x;
		int ys = y;

		newMazeData[x][y] = new Cell();
		wallList.add(new int[]{x+1, y});
		wallList.add(new int[]{x-1, y});
		wallList.add(new int[]{x, y+1});
		wallList.add(new int[]{x, y-1});
		Collections.shuffle(wallList);

		while (!wallList.isEmpty()){

			int[] cord = wallList.pop();
			x =cord[0]; y = cord[1];

			if (x < width-1 && x > 0 && y < height-1 && y > 0){

                int count = 0;

                if( newMazeData[x+1][y].isWall()){
                    count++; 
                }
                if( newMazeData[x-1][y].isWall() ){
                    count++;
                }
                if( newMazeData[x][y+1].isWall() ){
                    count++;
                }
                if( newMazeData[x][y-1].isWall() ){
                    count++;
                }

                if ( count == 3 ){

                    newMazeData[x][y] = new Cell(false);

                    if (!newMazeData[x+1][y].isWall()){

                        if (x > 1){
                            newMazeData[x-1][y] = new Cell(false);
                            if(newMazeData[x-2][y].isWall()){
                                wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x-2, y});
                            }
                        }

                        if(newMazeData[x-1][y+1].isWall()){
                            wallList.add((int) Math.round(wallList.size()*Math.random()), new int[]{x-1, y+1});
                        }

                        if(newMazeData[x-1][y-1].isWall()){
                            wallList.add((int) Math.round(wallList.size()*Math.random()), new int[]{x-1, y-1});
                        }
                    }

                    if (!newMazeData[x-1][y].isWall()){

                        if (x < width - 2){
                            newMazeData[x+1][y] = new Cell(false);
                            if(newMazeData[x+2][y].isWall()){
                                wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x+2, y});
                            }
                        }

                        if(newMazeData[x+1][y+1].isWall()){
                            wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x+1, y+1});
                        }

                        if(newMazeData[x+1][y-1].isWall()){
                            wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x+1, y-1});
                        }
                    }
                    if (!newMazeData[x][y+1].isWall()){

                        if (y > 1){
                            newMazeData[x][y-1] = new Cell(false);
                            if(newMazeData[x][y-2].isWall()){
                                wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x, y-2});
                            }
                        }

                        if(newMazeData[x+1][y-1].isWall()){
                            wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x+1, y-1});
                        }

                        if(newMazeData[x-1][y-1].isWall()){
                            wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x-1, y-1});
                        }

                    }

                    if (!newMazeData[x][y-1].isWall()){

                        if (y < height - 2){
                            newMazeData[x][y+1] = new Cell(false);
                            if(newMazeData[x][y+2].isWall()){
                                wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x, y+2});
                            }
                        }

                        if(newMazeData[x+1][y+1].isWall()){
                            wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x+1, y+1});
                        }

                        if(newMazeData[x-1][y+1].isWall()){
                            wallList.add((int) Math.round(wallList.size() * Math.random()), new int[]{x-1, y+1});
                        }
                    }
                }
            }
        }
        //newMazeData[xs][ys] = 10;
        setMaze(newMazeData);
    }
	

	/**
     * removes walls with a certain chance
     */
	public void easyfy(double chance){
		for (int x = 1; x < getWidth()-1; x++){
			for (int y = 1; y < getHeight()-1; y++){
				if (mazeData[x][y].isWall()){
					if(Math.random() > chance){
						mazeData[x][y] = new Cell(false);
                    }
                }
            }
        }
    }
	
	/**
     * returns true if all the non wall cells have been explored
     */
	public boolean isExplored(){
		for (int x = 1; x < getWidth()-1; x++){
			for (int y = 1; y < getHeight()-1; y++){
				if (!mazeData[x][y].isWall() && !mazeData[x][y].isFound()){
					return false;
				}}}
		return true;
	}
	
	/**
     * returns the number of explored cells
     */
	public int getExplored(){
		int ret = 0;
		for (int x = 1; x < getWidth()-1; x++){
			for (int y = 1; y < getHeight()-1; y++){
				if (mazeData[x][y].isFound()){
					ret++;
				}}}
		return ret;
	}
	
	/**
     * returns a random position which is not a wall
     */
	public int[] getFreePos(){
		List<int[]> freePos = new ArrayList<int[]>();
		for (int x = 1; x < getWidth()-1; x++){
			for (int y = 1; y < getHeight()-1; y++){
				if (!mazeData[x][y].isWall()){
					freePos.add(new int[]{x,y});
				}
			}}
		return freePos.get((int)(Math.random()*freePos.size()));
	}

	public static void main(String[] args){
		Maze maze = new Maze();
		maze.primsMaze(100, 100);
		//maze.easyfy(0.9);
		
		new MazeGui(maze);
	}
}
