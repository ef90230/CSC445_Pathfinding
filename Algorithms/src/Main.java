import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a grid of nodes (5x5 for simplicity)
        List<Node> allNodes = createGrid(5, 5);
        Node start = allNodes.get(0); // Starting node
        Node goal = allNodes.get(allNodes.size() - 1); // Goal node

        // Create the JFrame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maze Visualization with Obstacles");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);

            // Create the MazePanel
            MazePanel mazePanel = new MazePanel(allNodes, start, goal, 5, 5);

            // Create a button to start the A* algorithm
            JButton startButton = new JButton("Start A*");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Run the A* algorithm
                    List<Node> path = AStar.aStar(start, goal, allNodes, mazePanel);
                    mazePanel.setPath(path); // Update the path in the MazePanel
                    mazePanel.repaint(); // Repaint the panel to show the path
                }
            });

            // Add components to the frame
            frame.setLayout(new BorderLayout());
            frame.add(mazePanel, BorderLayout.CENTER);
            frame.add(startButton, BorderLayout.SOUTH);

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