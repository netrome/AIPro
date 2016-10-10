import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeGui extends JFrame {
    private MazeView view;

    public MazeGui(Maze maze, int pixelSize) {
    	super();
        view = new MazeView(maze, pixelSize);
        int widthDim = maze.getWidth() * pixelSize;
        int heightDim = maze.getHeight() * pixelSize;
        this.setPreferredSize(new Dimension(widthDim + 16, heightDim + 40));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(view);
        this.pack();
        this.setVisible(true);
    }

    public MazeGui(Maze maze) {
        this(maze, 5);
    }
    
    public void updateAgentPos(Agent[] agents){
    	int[][] tempPos = new int[agents.length][2];
    	for (int i = 0; i<agents.length; i++){
    		tempPos[i] = new int[]{agents[i].getX(),agents[i].getY()};
    	}
    	view.setAgentPos(tempPos);
    }
    
    public void changeMaze(Maze newMaze){
    	view.maze=newMaze;
    }


    private class MazeView extends JPanel {
        private Maze maze;
        private int width;
        private int[][] agentPositions = new int[0][0];

        public MazeView(Maze maze, int width) {
        	super();
            this.maze = maze;
            this.width = width;
        }
        
        public void setAgentPos(int[][] agentPos){
        	agentPositions=agentPos;
        }
    
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            for (int x = 0; x<maze.getMaze().length; x++){
                for (int y = 0; y<maze.getMaze()[0].length; y++){

                    g2.setPaint(Color.WHITE);

                    if (maze.getMaze()[x][y].isWall()){
                        g2.setPaint(Color.BLACK);
                    }
                    else if (maze.getCell(x,y).getPayload() == -1){
                        g2.setPaint(Color.gray);
                    }
                    else if (maze.getCell(x,y).getPayload() > 0){
                        int blue = (int)Math.round(maze.getCell(x,y).getPayload());
                        blue = 50+25*blue;
                        if(blue > 255 ) blue = 255;
                        g2.setPaint(new Color(0,0,blue));
                    }
                    else if (maze.getMaze()[x][y].isFound()){
                        g2.setPaint(Color.GREEN);
                    }
                    g2.fill(new Rectangle2D.Double(x*width, y*width,
                                width,width));
                }
            }
            for (int[] pos : agentPositions){
            	g2.setPaint(Color.RED);
            	g2.fill(new Rectangle2D.Double(pos[0]*width, pos[1]*width,
                        width,width));
            }

        }
    }
}



