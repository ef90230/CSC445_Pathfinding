import java.util.*;

public class AStar {

    public static List<Node> aStar(Node start, Node goal, List<Node> allNodes, MazePanel mazePanel) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        // Initialize the start node
        start.g = 0;
        start.h = heuristic(start, goal);
        start.calculateF();
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Debug: Current node being processed
            System.out.println("Processing node: (" + current.x + ", " + current.y + ")");

            // If the goal is reached, reconstruct the path
            if (current.equals(goal)) {
                System.out.println("Goal reached! Reconstructing path...");
                return reconstructPath(current);
            }

            closedSet.add(current);

            // Process neighbors
            for (Node neighbor : getNeighbors(current, allNodes, mazePanel)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.g + distance(current, neighbor);

                // If the neighbor is not in the open set or a better path is found
                if (!openSet.contains(neighbor) || tentativeG < neighbor.g) {
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, goal);
                    neighbor.calculateF();
                    neighbor.parent = current; // Set the parent to reconstruct the path

                    // Debug: Parent assignment
                    System.out.println("Setting parent of (" + neighbor.x + ", " + neighbor.y + ") to (" + current.x + ", " + current.y + ")");

                    // Add the neighbor to the open set if it's not already there
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                        System.out.println("Adding to openSet: (" + neighbor.x + ", " + neighbor.y + ")");
                    }
                }
            }
        }

        // Return an empty path if no path is found
        System.out.println("No path found!");
        return Collections.emptyList();
    }

    // Heuristic function (Manhattan distance)
    private static int heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    // Distance function (uniform cost)
    private static int distance(Node a, Node b) {
        return 1; // Modify if edge weights are needed
    }

    // Get neighbors of the current node
    private static List<Node> getNeighbors(Node node, List<Node> allNodes, MazePanel mazePanel) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Right, Down, Left, Up
    
        for (int[] dir : directions) {
            int newX = node.x + dir[0];
            int newY = node.y + dir[1];
    
            // Check if the new position is within bounds
            if (newX >= 0 && newX < mazePanel.getRows() && newY >= 0 && newY < mazePanel.getCols()) {
                Node neighbor = findNode(allNodes, newX, newY);
                if (neighbor != null && !isObstacle(neighbor, mazePanel)) {
                    neighbors.add(neighbor);
                    // Debug: Neighbor added
                    System.out.println("Neighbor added: (" + neighbor.x + ", " + neighbor.y + ")");
                }
            }
        }
        return neighbors;
    }
    
    // Helper method to find a node by its coordinates
    private static Node findNode(List<Node> allNodes, int x, int y) {
        for (Node node : allNodes) {
            if (node.x == x && node.y == y) {
                return node;
            }
        }
        return null;
    }

    // Check if a node is an obstacle
    private static boolean isObstacle(Node node, MazePanel mazePanel) {
        boolean result = mazePanel.isObstacle(node.x, node.y);
        // Debug: Obstacle check
        System.out.println("Checking obstacle at (" + node.x + ", " + node.y + "): " + result);
        return result;
    }

    // Reconstruct the path from the goal to the start
    private static List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            System.out.println("Reconstructing path: (" + current.x + ", " + current.y + ")"); // Debug
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path); // Reverse the path to go from start to goal
        return path;
    }
}