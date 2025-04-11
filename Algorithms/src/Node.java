//TODO: Implement GUI visualization for any node
//TODO: Add ability to add nodes, obstacles

public class Node implements Comparable<Node> {
    int x, y;
    int g, h, f;
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int calculateF() {
        this.f = this.g + this.h;
        return this.f;
    }
    
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}