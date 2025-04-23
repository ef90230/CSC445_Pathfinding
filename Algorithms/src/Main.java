import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a grid of nodes (10x10 for larger grid)
        List<Node> allNodes = createGrid(10, 10);
        Node start = allNodes.get(0); // Starting node
        Node goal = allNodes.get(allNodes.size() - 1); // Goal node

        // Create the JFrame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Visualization with Obstacles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800); // Adjust frame size for larger grid

            // Create the MazePanel
            MazePanel mazePanel = new MazePanel(allNodes, start, goal, 10, 10); // Updated grid size

            // Create buttons and add them to the frame
            JButton aStarButton = new JButton("Start A*");
            aStarButton.addActionListener(_ -> {
                List<Node> path = AStar.aStar(start, goal, allNodes, mazePanel);
                mazePanel.setPath(path);
            });

            JButton dijkstraButton = new JButton("Start Dijkstra");
            dijkstraButton.addActionListener(_ -> {
                List<Node> path = Dijkstra.dijkstra(start, goal, allNodes, mazePanel);
                mazePanel.setPath(path);
            });

            JButton resetPathButton = new JButton("Reset Path");
            resetPathButton.addActionListener(_ -> {
                mazePanel.setPath(null); // Clear the path only
                System.out.println("Path cleared, obstacles remain intact.");
            });

            JButton resetObstaclesButton = new JButton("Reset Obstacles");
            resetObstaclesButton.addActionListener(_ -> {
                mazePanel.clearObstacles(); // Clear all obstacles
                System.out.println("All obstacles cleared.");
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(aStarButton);
            buttonPanel.add(dijkstraButton);
            buttonPanel.add(resetPathButton);
            buttonPanel.add(resetObstaclesButton);

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