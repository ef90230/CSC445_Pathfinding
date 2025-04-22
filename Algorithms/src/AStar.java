import java.util.*;

public class AStar {

    public static List<Node> aStar(Node start, Node goal, List<Node> allNodes) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        start.g = 0;
        start.h = heuristic(start, goal);
        start.calculateF();
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(current, allNodes)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.g + distance(current, neighbor);

                if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, goal);
                    neighbor.calculateF();
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList(); // Return an empty path if no path is found
    }

    private static int heuristic(Node a, Node b) {
        // Example heuristic: Manhattan distance
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private static int distance(Node a, Node b) {
        // Assuming uniform cost for simplicity; modify if edge weights are needed
        return 1;
    }

    private static List<Node> getNeighbors(Node node, List<Node> allNodes) {
        List<Node> neighbors = new ArrayList<>();
        for (Node n : allNodes) {
            if (distance(node, n) == 1) { // Example: neighbors are adjacent nodes
                neighbors.add(n);
            }
        }
        return neighbors;
    }

    private static List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}