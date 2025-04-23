import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MazePanel extends JPanel {
    private final List<Node> allNodes;
    private final Node start, goal;
    private final int rows, cols;
    private List<Node> path;
    private final boolean[][] obstacles; // 2D array to track obstacles

    public MazePanel(List<Node> allNodes, Node start, Node goal, int rows, int cols) {
        this.allNodes = allNodes;
        this.start = start;
        this.goal = goal;
        this.rows = rows;
        this.cols = cols;
        this.obstacles = new boolean[rows][cols];

        // Add mouse listener to toggle obstacles
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellWidth = getWidth() / cols;
                int cellHeight = getHeight() / rows;
                int x = e.getX() / cellWidth;
                int y = e.getY() / cellHeight;

                // Toggle obstacle, but don't allow start or goal to be obstacles
                if (!(start.x == x && start.y == y) && !(goal.x == x && goal.y == y)) {
                    obstacles[x][y] = !obstacles[x][y];
                    Node node = findNode(allNodes, x, y);
                    if (node != null) {
                        node.setObstacle(obstacles[x][y]); // Update the Node's obstacle status
                        System.out.println("Obstacle toggled at: (" + x + ", " + y + ") -> " + obstacles[x][y]);
                    }
                    repaint();
                }
            }
        });
    }

    public void setPath(List<Node> path) {
        this.path = path;
        repaint(); // Repaint the panel to reflect the updated path
    }

    public boolean isObstacle(int x, int y) {
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return true; // Treat out-of-bounds as obstacles
        }
        return obstacles[x][y];
    }

    public void setObstaclesManually(int[][] obstacleCoordinates) {
        // Clear existing obstacles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                obstacles[i][j] = false;
            }
        }

        // Set new obstacles
        for (int[] coord : obstacleCoordinates) {
            int x = coord[0];
            int y = coord[1];
            if (x >= 0 && x < rows && y >= 0 && y < cols) {
                obstacles[x][y] = true;
                Node node = findNode(allNodes, x, y);
                if (node != null) {
                    node.setObstacle(true); // Update the Node's obstacle status
                    System.out.println("Obstacle set at: (" + x + ", " + y + ")");
                }
            }
        }
        repaint();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        // Draw grid
        for (Node node : allNodes) {
            int x = node.x * cellWidth;
            int y = node.y * cellHeight;

            // Draw obstacles
            if (obstacles[node.x][node.y]) {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, cellWidth, cellHeight);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, cellWidth, cellHeight);
            }

            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellWidth, cellHeight);
        }

        // Highlight start and goal
        g.setColor(Color.GREEN);
        g.fillRect(start.x * cellWidth, start.y * cellHeight, cellWidth, cellHeight);
        g.setColor(Color.BLUE);
        g.fillRect(goal.x * cellWidth, goal.y * cellHeight, cellWidth, cellHeight);

        // Highlight path
        if (path != null) {
            g.setColor(Color.RED);
            for (Node node : path) {
                int x = node.x * cellWidth;
                int y = node.y * cellHeight;
                g.fillRect(x, y, cellWidth, cellHeight);
                System.out.println("Path node: (" + node.x + ", " + node.y + ")");
            }
        }
    }

    // Helper method to find a node by its coordinates
    private Node findNode(List<Node> allNodes, int x, int y) {
        for (Node node : allNodes) {
            if (node.x == x && node.y == y) {
                return node;
            }
        }
        return null;
    }

    // Method to clear all obstacles
    public void clearObstacles() {
        // Clear the obstacles array
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                obstacles[i][j] = false; // Clear the obstacle in the grid
            }
        }
    
        // Update the Node objects to reflect the cleared obstacles
        for (Node node : allNodes) {
            node.setObstacle(false); // Assuming Node has a setObstacle method
        }
    
        repaint(); // Refresh the panel to reflect changes
    }
}