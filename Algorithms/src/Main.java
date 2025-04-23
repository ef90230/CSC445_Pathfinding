import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a grid of nodes (5x5 for simplicity)
        List<Node> allNodes = createGrid(5, 5);
        Node start = allNodes.get(0); // Starting node
        Node goal = allNodes.get(allNodes.size() - 1); // Goal node

        // Set obstacles manually
        int[][] obstacleCoordinates = {
            {1, 1}, {1, 2}, {1, 3}, // Row 1 obstacles
            {2, 3}, {3, 3}, {4, 3}  // Column 3 obstacles
        };

        // Create the JFrame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Visualization with Obstacles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);

            // Create the MazePanel
            MazePanel mazePanel = new MazePanel(allNodes, start, goal, 5, 5);
            mazePanel.setObstaclesManually(obstacleCoordinates); // Set obstacles in the MazePanel

            // Create a button to start the A* algorithm
            JButton aStarButton = new JButton("Start A*");
            aStarButton.addActionListener(_ -> {
                List<Node> path = AStar.aStar(start, goal, allNodes, mazePanel);
                mazePanel.setPath(path); // Update the path in the MazePanel
            });

            // Create a button to start Dijkstra's algorithm
            JButton dijkstraButton = new JButton("Start Dijkstra");
            dijkstraButton.addActionListener(_ -> {
                List<Node> path = Dijkstra.dijkstra(start, goal, allNodes, mazePanel);
                mazePanel.setPath(path); // Update the path in the MazePanel
            });

            // Create a reset button to clear only the path
            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(_ -> {
                mazePanel.setPath(null); // Clear the path only
                System.out.println("Path cleared, obstacles remain intact.");
            });

            // Add components to the frame
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(aStarButton);
            buttonPanel.add(dijkstraButton);
            buttonPanel.add(resetButton);

            frame.setLayout(new BorderLayout());
            frame.add(mazePanel, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    // Helper method to create a grid of nodes
    private static List<Node> createGrid(int rows, int cols) {
        List<Node> nodes = new java.util.ArrayList<>();
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                nodes.add(new Node(x, y));
            }
        }
        return nodes;
    }
}