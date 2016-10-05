import java.util.LinkedList;
import java.util.Collections;

public class Maze_old {
	private int[][] mazeData;
	
	public Maze_old(){
	}
	
	public Maze_old(int[][] mazeData){
		this.setMaze(mazeData);
	}

	public int[][] getMaze() {
		return mazeData;
	}

	public void setMaze(int[][] mazeData) {
		this.mazeData = mazeData;
	}
	
	public void randomMaze(int width,int height){
		int[][] newMazeData = new int[width][height];
		for (int x = 0;x<width;x++){
			for (int y = 0;y<height;y++){
				newMazeData[x][y]=0;
				if (Math.random()>0.7){
					newMazeData[x][y]=1;
				}}}
		setMaze(newMazeData);
	}
	
	public int getWidth(){
		return mazeData.length;
	}
	
	public int getHeight(){
		return mazeData[0].length;
	}
	
	public void primsMaze(int width,int height){
		int[][] newMazeData = new int[width][height];
		for (int x = 0;x<width;x++){
			for (int y = 0;y<height;y++){
				newMazeData[x][y]=1;
		}}
		LinkedList <int[]> wallList = new LinkedList<int[]>();
		int x=(int) Math.round(1+Math.random()*(width-2));
		int y=(int) Math.round(1+Math.random()*(height-2));
		int xs = x;
		int ys = y;
		newMazeData[x][y]=0;
		wallList.add(new int[]{x+1,y});
		wallList.add(new int[]{x-1,y});
		wallList.add(new int[]{x,y+1});
		wallList.add(new int[]{x,y-1});
		Collections.shuffle(wallList);
		while (!wallList.isEmpty()){
			int[] cord = wallList.pop();
			x =cord[0]; y = cord[1];
			if (x<width-1 && x>0 && y<height-1 && y>0){
			if (newMazeData[x+1][y]+newMazeData[x-1][y]+newMazeData[x][y+1]+newMazeData[x][y-1]==3){
				newMazeData[x][y]=0;
				if (newMazeData[x+1][y]==0){
					if (x>1){
						newMazeData[x-1][y]=0;
						if(newMazeData[x-2][y]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x-2,y});
					}
					if(newMazeData[x-1][y+1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x-1,y+1});
					if(newMazeData[x-1][y-1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x-1,y-1});
				}
				if (newMazeData[x-1][y]==0){
					if (x<width-2){
						newMazeData[x+1][y]=0;
						if(newMazeData[x+2][y]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x+2,y});
					}
					if(newMazeData[x+1][y+1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x+1,y+1});
					if(newMazeData[x+1][y-1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x+1,y-1});
				}
				if (newMazeData[x][y+1]==0){
					if (y>1){
						newMazeData[x][y-1]=0;
						if(newMazeData[x][y-2]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x,y-2});
					}
					if(newMazeData[x+1][y-1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x+1,y-1});
					if(newMazeData[x-1][y-1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x-1,y-1});
				}
				if (newMazeData[x][y-1]==0){
					if (y<height-2){
						newMazeData[x][y+1]=0;
						if(newMazeData[x][y+2]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x,y+2});
					}
					if(newMazeData[x+1][y+1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x+1,y+1});
					if(newMazeData[x-1][y+1]!=0)wallList.add((int)Math.round(wallList.size()*Math.random()),new int[]{x-1,y+1});
				}
			}}
		}
		newMazeData[xs][ys] = 10;
		setMaze(newMazeData);
	}
	
	public void easyfy(double chance){
		for (int x = 0;x<getWidth();x++){
			for (int y = 0;y<getHeight();y++){
				if (mazeData[x][y]==1){
					if(Math.random()>chance){
						mazeData[x][y]=0;
						}}}}
	}

	/*
	public static void main(String[] args){
		int[][] mazeData = {{0,1,0,1,0},{0,1,0,1,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,1}};
		Maze maze = new Maze();
		System.out.println("here");
		maze.primsMaze(100, 100);
		maze.easyfy(0.9);
		
		new MazeGui(maze);
	}*/

}
