import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    static int size = 10; // Size of the grid
    public static void main(String[] args) {
        // Create a grid of nodes 
        List<Node> allNodes = createGrid(size, size);
        Node start = allNodes.get(0); // Starting node
        Node goal = allNodes.get(allNodes.size() - 1); // Goal node

        // Create the JFrame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Visualization with Obstacles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800); // Adjust frame size for larger grid

            // Create the MazePanel
            MazePanel mazePanel = new MazePanel(allNodes, start, goal, size, size); // Updated grid size

            // Create buttons and add them to the frame
            JButton setStartButton = new Button("resources\\SetStartButtons\\Set Start button (no hover).png", "resources\\SetStartButtons\\Set Start button (hover).png", "resources\\SetStartButtons\\Set Start button (click).png");
            setStartButton.setDisabledIcon(new ImageIcon("resources\\SetStartButtons\\Set Start button (in use).png"));

            JButton setEndButton = new Button("resources\\SetEndButtons\\Set End button (no hover).png", "resources\\SetEndButtons\\Set End button (hover).png", "resources\\SetEndButtons\\Set End button (click).png");
            setEndButton.setDisabledIcon(new ImageIcon("resources\\SetEndButtons\\Set End button (in use).png"));

            JButton setObstaclesButton = new Button("resources\\SetObstaclesButtons\\Set Obstacle button (no hover).png", "resources\\SetObstaclesButtons\\Set Obstacle button (hover).png", "resources\\SetObstaclesButtons\\Set Obstacle button (click).png");
            setObstaclesButton.setDisabledIcon(new ImageIcon("resources\\SetObstaclesButtons\\Set Obstacle button (in use).png"));

            JButton setWeightsButton = new Button("resources\\SetWeightButtons\\Set Weights button (no hover).png", "resources\\SetWeightButtons\\Set Weights button (hover).png", "resources\\SetWeightButtons\\Set Weights button (click).png");
            setWeightsButton.setDisabledIcon(new ImageIcon("resources\\SetWeightButtons\\Set Weights button (in use).png"));

            setStartButton.addActionListener(_ -> {
                mazePanel.setAction("Set Start"); // Update the action in MazePanel
                System.out.println("Selected action: Set Start");
                setStartButton.setEnabled(false);
                setEndButton.setEnabled(true);
                setObstaclesButton.setEnabled(true);
                setWeightsButton.setEnabled(true); // Enable the "Set Weights" button
            });
            setEndButton.addActionListener(_ -> {
                mazePanel.setAction("Set End"); // Update the action in MazePanel
                System.out.println("Selected action: Set End");
                setStartButton.setEnabled(true);
                setEndButton.setEnabled(false);
                setObstaclesButton.setEnabled(true);
                setWeightsButton.setEnabled(true); // Enable the "Set Weights" button
            });
            setObstaclesButton.addActionListener(_ -> {
                mazePanel.setAction("Place Obstacles"); // Update the action in MazePanel
                System.out.println("Selected action: Place Obstacles");
                setStartButton.setEnabled(true);
                setEndButton.setEnabled(true);
                setObstaclesButton.setEnabled(false);
                setWeightsButton.setEnabled(true); // Enable the "Set Weights" button
            });
            setWeightsButton.addActionListener(_ -> {
                mazePanel.setAction("Set Weight"); // Update the action in MazePanel
                System.out.println("Selected action: Set Weight");
                setStartButton.setEnabled(true);
                setEndButton.setEnabled(true);
                setObstaclesButton.setEnabled(true);
                setWeightsButton.setEnabled(false); // Disable the "Set Weight" button
            });

            JButton aStarButton = new Button("resources\\StartAStarButtons\\Start A_ button (no hover).png", "resources\\StartAStarButtons\\Start A_ button (hover).png", "resources\\StartAStarButtons\\Start A_ button (click).png");
            aStarButton.addActionListener(_ -> {
                List<Node> path = AStar.aStar(mazePanel, allNodes);
                mazePanel.setPath(path);
            });

            JButton dijkstraButton = new Button("resources\\StartDijkstraButtons\\Start Dijkstra button (no hover).png", "resources\\StartDijkstraButtons\\Start Dijkstra button (hover).png", "resources\\StartDijkstraButtons\\Start Dijkstra button (click).png");
            dijkstraButton.addActionListener(_ -> {
                List<Node> path = Dijkstra.dijkstra(mazePanel, allNodes);
                mazePanel.setPath(path);
            });

            JButton resetPathButton = new Button("resources\\ResetPathButtons\\Reset Path button (no hover).png", "resources\\ResetPathButtons\\Reset Path button (hover).png", "resources\\ResetPathButtons\\Reset Path button (click).png");
            resetPathButton.addActionListener(_ -> {
                mazePanel.setPath(null); // Clear the path only
                System.out.println("Path cleared, obstacles remain intact.");
            });

            JButton resetObstaclesButton = new Button("resources\\ResetObstaclesButtons\\Reset Obstacles button (no hover).png", "resources\\ResetObstaclesButtons\\Reset Obstacles button (hover).png", "resources\\ResetObstaclesButtons\\Reset Obstacles button (click).png");
            resetObstaclesButton.addActionListener(_ -> {
                mazePanel.clearObstacles(); // Clear all obstacles
                System.out.println("All obstacles cleared.");
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(setObstaclesButton);
            buttonPanel.add(setStartButton);
            buttonPanel.add(setEndButton);
            buttonPanel.add(setWeightsButton);
            buttonPanel.add(aStarButton);
            buttonPanel.add(dijkstraButton);
            buttonPanel.add(resetPathButton);
            buttonPanel.add(resetObstaclesButton);
            setObstaclesButton.setEnabled(false);

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