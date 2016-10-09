import java.util.*;

public class AStar {


    /**
     * A* search based on a heuristic h(n)
     *
     * f(n) = h(n) + g(n)
     *
     * where f(n) is the cost from the start to n and g(n) is the
     * cost from n to the goal
     *
     * @param start: the start state
     * @param goal: the goal state
     * @param f: the heuristic
     * @return: The optimal path as a list of states (from start to goal). Returns empty list if no path was found
     */
    public static List<State> search(State start, State goal, Successor s, Heuristic f){

        // All visited states
        Set<State> visited = new HashSet<>();

        // Best predecessor state
        Map<State, State> predecessor = new HashMap<>();

        // Cost from the start to the goal
        Map<State, Double> costFromGoal = new HashMap<>();

        // States to be evaluated
        PriorityQueue<State> heap = new PriorityQueue<>(0, new AStar.CostComparator(costFromGoal, f));

        while(!heap.isEmpty()){

            // Get state with lowest cost value
            State current = heap.poll();

            // Add to visited
            visited.add(current);

            // Check if goal
            if (current == goal){
                return reconstructPath(current, predecessor);
            }

            // Get next possible states
            List<State> nextPossibleStates = s.successors(current);

            for (State neighbour : nextPossibleStates){

                // Don't check already visited
                if(visited.contains(n)){
                    continue;
                }

                // Calculate cost from goal
                double tentativeCostFromGoal = costFromGoal.get(current) + f.distance(current, neighbour);

                // Add to heap if not seen before
                if (!heap.contains(neighbour)){
                    heap.add(neighbour);
                }
                // If already seen and more costly than before
                else if(tentativeCostFromGoal >= costFromGoal.get(neighbour)){
                    continue;
                }

                // Else this is the best path until now

                // Current was the predecessor
                predecessor.put(neighbour, current);

                // Store the cost from goal
                costFromGoal.put(neighbour, tentativeCostFromGoal);
            }

            // If no path was found return empty list
            return new ArrayList<>();

        }


        return null;
    }

    private static List<State> reconstructPath(State lastState, Map<State, State> predecessor){

        List<State> path = new ArrayList<>();

        State s = lastState;

        while (!predecessor.containsKey(s)){
            path.add(0, s);
            s = predecessor.get(s);
        }

        path.add(0, s);

        return path;
    }


    public interface Heuristic{
        public double distance(State s1, State s2);
        public double costToGoal(State s);
    }


    static private class CostComparator implements Comparator<State>{

        Heuristic f;
        Map<State, Double> costFromStart;

        private CostComparator(Map<State, Double> costFromStart, Heuristic f){
            this.f = f;
            this.costFromStart = costFromStart;
        }

        public int compare(State s1, State s2) {
            double cost1 = costFromStart.get(s1) + f.costToGoal(s1);
            double cost2 = costFromStart.get(s2) + f.costToGoal(s2);

            return (int) (cost1 - cost2);
        }

    }

    public interface Successor{
        public List<State> successors(State s);
    }




}
