public class Node implements Comparable<Node> {
    int x, y; // Coordinates of the node
    int g, h, f; // Costs for A* algorithm
    Node parent; // Reference to the parent node for path reconstruction
    boolean isObstacle; // Flag to indicate if the node is an obstacle

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = Integer.MAX_VALUE; // Initialize g to a large value (infinity)
        this.h = 0;
        this.f = Integer.MAX_VALUE; // Initialize f to a large value (infinity)
        this.parent = null;
        this.isObstacle = false; // Default to not being an obstacle
    }

    public int calculateF() {
        this.f = this.g + this.h;
        return this.f;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
        // Debug: Log when the obstacle status is updated
        System.out.println("Node (" + x + ", " + y + ") obstacle set to: " + isObstacle);
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f); // Compare nodes based on their f value
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y; // Nodes are equal if their coordinates match
    }

    @Override
    public int hashCode() {
        return 31 * x + y; // Generate a unique hash code based on coordinates
    }

    @Override
    public String toString() {
        return "Node(" +
                "x=" + x +
                ", y=" + y +
                ", g=" + g +
                ", h=" + h +
                ", f=" + f +
                ", isObstacle=" + isObstacle +
                ")";
    }
}