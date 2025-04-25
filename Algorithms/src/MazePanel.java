import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MazePanel extends JPanel {
    private final List<Node> allNodes;
    private Node start, goal;
    private final int rows, cols;
    private List<Node> path;
    private final boolean[][] obstacles; // 2D array to track obstacles
    private String currentAction = "Place Obstacles"; // Default action

    public MazePanel(List<Node> allNodes, Node start, Node goal, int rows, int cols) {
        this.allNodes = allNodes;
        this.start = start; // Assign to instance variable
        this.goal = goal;   // Assign to instance variable
        this.rows = rows;
        this.cols = cols;
        this.obstacles = new boolean[rows][cols];

        // Add mouse listener to handle clicks based on the current action
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cellWidth = getWidth() / cols;
                int cellHeight = getHeight() / rows;
                int x = e.getX() / cellWidth;
                int y = e.getY() / cellHeight;

                if (x >= 0 && x < cols && y >= 0 && y < rows) {
                    switch (currentAction) {
                        case "Place Obstacles":
                            if (!(MazePanel.this.start.x == x && MazePanel.this.start.y == y) &&
                                !(MazePanel.this.goal.x == x && MazePanel.this.goal.y == y)) {
                                obstacles[x][y] = !obstacles[x][y];
                                Node node = findNode(allNodes, x, y);
                                if (node != null) {
                                    node.setObstacle(obstacles[x][y]);
                                    System.out.println("Obstacle toggled at: (" + x + ", " + y + ") -> " + obstacles[x][y]);
                                }
                                repaint();
                            }
                            break;
                        case "Set Start":
                            if (!obstacles[x][y] && !(MazePanel.this.goal.x == x && MazePanel.this.goal.y == y)) {
                                MazePanel.this.start = findNode(allNodes, x, y);
                                System.out.println("Start node set to: (" + x + ", " + y + ")");
                                repaint();
                            }
                            break;
                        case "Set End":
                            if (!obstacles[x][y] && !(MazePanel.this.start.x == x && MazePanel.this.start.y == y)) {
                                MazePanel.this.goal = findNode(allNodes, x, y);
                                System.out.println("End node set to: (" + x + ", " + y + ")");
                                repaint();
                            }
                            break;
                            case "Set Weight":
                            Node node = findNode(allNodes, x, y);
                            if (node != null && !obstacles[x][y]) {
                                String input = JOptionPane.showInputDialog(MazePanel.this, "Enter weight for node (" + x + ", " + y + "):", "Set Weight", JOptionPane.PLAIN_MESSAGE);
                                if (input != null) {
                                    try {
                                        int weight = Integer.parseInt(input);
                                        if (weight > 0) {
                                            node.setWeight(weight);
                                            System.out.println("Weight set for node (" + x + ", " + y + "): " + weight);
                                            repaint();
                                        } else {
                                            JOptionPane.showMessageDialog(MazePanel.this, "Weight must be greater than 0.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(MazePanel.this, "Invalid weight. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        });
    }

    public void setAction(String action) {
        this.currentAction = action;
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
                    node.setObstacle(true);
                    System.out.println("Obstacle set at: (" + x + ", " + y + ")");
                }
            }
        }
        repaint();
    }

    public void clearObstacles() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                obstacles[i][j] = false;
            }
        }
        for (Node node : allNodes) {
            node.setObstacle(false);
        }
        repaint();
    }

    public void clearWeights() {
        for (Node node : allNodes) {
            node.setWeight(1);;
        }
        repaint();
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

            if (obstacles[node.x][node.y]) {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, cellWidth, cellHeight);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x, y, cellWidth, cellHeight);
            }

            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellWidth, cellHeight);

            // Display the weight if it's not 1
            if (node.getWeight() != 1) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 24)); // Set font for weight text
                String weightText = String.valueOf(node.getWeight());
                int textWidth = g.getFontMetrics().stringWidth(weightText);
                int textHeight = g.getFontMetrics().getHeight();
                g.drawString(weightText, x + (cellWidth - textWidth) / 2, y + (cellHeight + textHeight) / 2 - 4);
            }
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

    private Node findNode(List<Node> allNodes, int x, int y) {
        for (Node node : allNodes) {
            if (node.x == x && node.y == y) {
                return node;
            }
        }
        return null;
    }

    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }

    public Node getStart() {
        return start;
    }
    
    public Node getGoal() {
        return goal;
    }

    public void highlightNode(Node node) {
        Graphics g = getGraphics();
        if (g != null) {
            int cellWidth = getWidth() / cols;
            int cellHeight = getHeight() / rows;
            int x = node.x * cellWidth;
            int y = node.y * cellHeight;
    
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, cellWidth, cellHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellWidth, cellHeight);
        }
    }
}