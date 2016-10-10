import java.util.*;

public class AStar {


    /**
     * A* search based on a heuristic
     *
     * where f(n) is the cost from the start to n and g(n) is the
     * cost from n to the goal
     *
     * @param start: the start state
     * @param s: successor function
     * @param h: the heuristic
     * @param maxDpeth : the maximum depth
     * @return: The optimal path as a list of states (from start to goal). Returns empty list if no path was found
     */
    public static List<State> search(State start, Successor s, Heuristic h, int maxDpeth){


        // All visited states
        Set<String> visited = new HashSet<>();

        // Best predecessor state
        Map<State, State> predecessor = new HashMap<>();

        // Cost from the start to a state
        Map<String, Double> costFromStart = new HashMap<>();

        // Total cost for a state
        Map<State, Double> cost = new HashMap<>();

        // States to be evaluated
        PriorityQueue<State> heap = new PriorityQueue<>(1, new AStar.CostComparator(cost));

        // Add start to heap
        heap.add(start);

        // Cost to start is 0
        costFromStart.put(start.toString(), new Double(0));

        // depth
        Map<State, Integer> depth = new HashMap<>();

        depth.put(start, 0);

        while(!heap.isEmpty()){
            // Get state with lowest cost value
            State current = heap.poll();
            String currentString = current.toString();

            // Add to visited
            visited.add(current.toString());

            // Check if goal
            if (s.isGoal(current) || depth.get(current) >= maxDpeth){
                return reconstructPath(current, predecessor);
            }

            // Get next possible states
            List<State> nextPossibleStates = s.successors(current);


            for (State neighbour : nextPossibleStates){

                String neighbourString = neighbour.toString();

                // Don't check already visited
                if(visited.contains(neighbourString)){
                    continue;
                }

                // Calculate cost from goal
                double tentativeCostFromStart = costFromStart.get(currentString) + h.distance(current, neighbour);


                // If already seen ad more costly than before
                if(costFromStart.containsKey(neighbourString) && tentativeCostFromStart >= costFromStart.get(neighbourString)){
                    continue;
                }

                // Else this is the best or only path until now

                // Current was the predecessor
                predecessor.put(neighbour, current);

                // Add to depth
                depth.put(neighbour, depth.get(current) + 1);

                // Store the cost from goal
                costFromStart.put(neighbourString, tentativeCostFromStart);

                // Store total cost
                cost.put(neighbour, tentativeCostFromStart + h.costToGoal(neighbour));

                // Add to heap if not seen before
                if (!heap.contains(neighbour)){
                    heap.add(neighbour);
                }

            }

        }


        // If no path was found return empty list
        return new ArrayList<>();
    }

    private static List<State> reconstructPath(State lastState, Map<State, State> predecessor){

        List<State> path = new ArrayList<>();

        State s = lastState;

        while (predecessor.containsKey(s)){
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

    public interface Successor{
        public List<State> successors(State s);
        public boolean isGoal(State s);
    }

    static private class CostComparator implements Comparator<State>{

        Map<State, Double> cost;

        private CostComparator(Map<State, Double> cost){
            this.cost = cost;
        }

        public int compare(State s1, State s2) {
            return (int) (cost.get(s1) - cost.get(s2));
        }

    }



}
