import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeGui extends JFrame{
	private MazeView view;
	
	public MazeGui(Maze maze, int pixelSize){
		view = new MazeView(maze,pixelSize);
		int widthDim = maze.getWidth() * pixelSize;
		int heightDim = maze.getHeight() * pixelSize;
		this.setPreferredSize(new Dimension(widthDim+16, heightDim+40));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(view);
		this.pack();
		this.setVisible(true);
	}
	
	public MazeGui(Maze maze){
		this(maze,5);
	}
	
	
	
	
	
	
	
	
private class MazeView extends JPanel{
	private Maze maze;
	private int width;
	
	public MazeView(Maze maze, int width){
		this.maze = maze;
		this.width = width;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (int x = 0; x<maze.getMaze().length; x++){
			for (int y = 0; y<maze.getMaze()[0].length; y++){
				g2.setPaint(Color.WHITE);
				if (maze.getMaze()[x][y]==10){
				g2.setPaint(Color.RED);
				}
				else if (maze.getMaze()[x][y]!=0){
					g2.setPaint(Color.BLACK);
				}
				g2.fill(new Rectangle2D.Double(x*width, y*width,
						width,width));
			}
		}
}
}
}
