import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a grid of nodes (10x10 for larger grid)
        //If you change the grid size, make sure to adjust the MazelPanel constructor accordingly
        List<Node> allNodes = createGrid(10, 10);
        Node start = allNodes.get(0); // Starting node
        Node goal = allNodes.get(allNodes.size() - 1); // Goal node

        // Create the JFrame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Visualization with Obstacles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800); // Adjust frame size for larger grid

            // Create the MazePanel
            MazePanel mazePanel = new MazePanel(allNodes, start, goal, 10, 10); // Updated grid size

            // Create a dropdown menu for selecting actions
            String[] options = {"Place Obstacles", "Set Start", "Set End"};
            JComboBox<String> actionSelector = new JComboBox<>(options);
            actionSelector.addActionListener(_ -> {
                String selectedAction = (String) actionSelector.getSelectedItem();
                mazePanel.setAction(selectedAction); // Update the action in MazePanel
                System.out.println("Selected action: " + selectedAction);
            });

            // Create buttons and add them to the frame
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
            buttonPanel.add(actionSelector);
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