import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Player uses an A* search for multiple agents
 */
public class GlobalPlayer implements Player {

    private State goal;

    private List<State> previousPath = null;

    private int count = 0;

    private MazeGui gui = null;
    private MazeGui pathGui = null;


    public GlobalPlayer(){

    }

    /**
     *
     * @param showPath      -   gui that shows path
     * @param showSearch    -   gui that shows search
     * @param width         -   width of gui window
     * @param height        -   height of gui window
     */
    public GlobalPlayer(boolean showPath, boolean showSearch, int width, int height){
        Maze m;
        if (showPath) {
            m = new Maze();
            m.randomMaze(width, height);
            pathGui = new MazeGui(m);
        }

        if (showSearch) {
            m = new Maze();
            m.randomMaze(width, height);
            gui = new MazeGui(m);
        }

    }

    @Override
    public State play(State state) {
        state.maze.inferCells(); // Make sure we don't get stuck

        List<State> path;

        // Search for new path if new cells were explored
        if(previousPath == null || previousPath.size() == 1 ||
                previousPath.get(0).maze.getExplored() != state.maze.getExplored()){
            path = AStar.search(state, new GlobalSuccessor(state), new GlobalHeuristic(state), Integer.MAX_VALUE, gui);
        }
        // Otherwise use old path
        else{
            path = previousPath;
            path.remove(0);
        }

        // If there is no path or agents at goal
        if(path.size() == 0 || path.size() == 1){
            return state;
        }


        previousPath = path;


        if (pathGui != null) {
            State pathState = state.clone();
            paintPath(pathState.maze, path);
            pathGui.updateAgentPos(pathState.agents);
            pathGui.changeMaze(pathState.maze);
            pathGui.repaint();
        }

        if (gui != null) {
            paintMaze(path.get(1).maze);
        }

        return path.get(1);
    }



    private void paintMaze(Maze m){
        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                double d =distanceToUndiscoveredCell(m, x, y);
                if(d > m.getHeight() * m.getWidth()){
                    d = m.getHeight() * m.getWidth();
                }
                m.getCell(x, y).setPayload(d);
            }
        }
    }



    private void paintPath(Maze m, List<State> path){

        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                m.getCell(x, y).setPayload(0);
            }
        }

        for (State step : path){
            for (Agent a : step.agents){
                Cell c = m.getCell(a.getX(), a.getY());
                c.setPayload(c.getPayload() + 100);
            }
        }
    }


    private double distanceToUndiscoveredCell(Maze maze, int xPos, int yPos){

        double closest = Double.MAX_VALUE;

        for (int x = 0; x < maze.getWidth(); x++){
            for (int y = 0; y < maze.getHeight(); y++){

                Cell c = maze.getCell(x, y);

                if(!c.isFound()){

                    double d = Math.pow(x - xPos, 2) + Math.pow(y - yPos, 2);

                    if(d < closest){
                        closest = d;
                    }
                }

            }

        }

        return closest;
    }

    private double distanceBetweenAgents(State s){
        double d = 0;
        for (Agent a : s.agents){
            d += distanceToAgents(a, s.agents);
        }
        return d;
    }

    private double distanceToAgents(Agent a, Agent[] otherAgents){

        double d = 0;

        for (Agent oa : otherAgents){
            if(oa != a) {
                double td = Math.pow(a.getX() - oa.getX(), 2) + Math.pow(a.getY() - oa.getY(), 2);
                if (td < 10 * 10){
                    d += td;
                }
            }
        }
        return d;
    }

    private class GlobalSuccessor implements AStar.Successor{

        private State start;

        GlobalSuccessor(State start){
            this.start = start;
        }

        public List<State> successors(State s){

            List<Integer> fixedAgents = new ArrayList<>();

            for (int a = 0; a < s.agents.length; a++){
                if (agentOnBoarder(start.maze, s.agents[a])){
                    fixedAgents.add(a);
                }
            }

            return s.findPossibleMoves(fixedAgents);

        }

        public boolean isGoal(State s){

            if (s.maze.isExplored()){
                return true;
            }

            for (Agent a : s.agents){
                if (!agentOnBoarder(start.maze, a)){
                    return false;
                }
            }

            return true;
        }

        private boolean agentOnBoarder(Maze m, Agent a){

            for (int[] coord: m.getNeighbours(a.getX(), a.getY())){

                if(!m.getCell(coord[0], coord[1]).isFound()){
                    return true;
                }

            }

            return false;
        }

    }

    private class GlobalHeuristic implements AStar.Heuristic{
        private State start;

        GlobalHeuristic(State start){
            this.start = start;
        }


        public double distance(State s1, State s2){

            double moveCost = 10;
            double explored = (s1.maze.getExplored() - s2.maze.getExplored());
            double dAgents = (distanceBetweenAgents(s1) - distanceBetweenAgents(s2));
            //dAgents /= (Math.pow(s1.maze.getHeight(), 2) + Math.pow(s1.maze.getWidth(), 2));

            return moveCost + explored; //+ 0.1 * dAgents;
        }

        public double costToGoal(State s){

            double sum = 0;
            for (Agent a : s.agents){
                double d = distanceToUndiscoveredCell(start.maze, a.getX(), a.getY());
                //d /= (Math.pow(s.maze.getHeight(), 2) + Math.pow(s.maze.getWidth(), 2));
                if(d==1){
                    sum -= 100000;
                }else{
                    sum += d;
                }
            }
            return sum;
        }

    }

    @Override
    public String toString() {
        return "GlobalPlayer";
    }
}
