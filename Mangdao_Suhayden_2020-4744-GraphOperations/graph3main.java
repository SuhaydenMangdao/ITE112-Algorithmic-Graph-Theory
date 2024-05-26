import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.ArrayList;

public class Graph3 {
    public int[][] adjacencyMatrix;
    private int numNodes;
    private int numEdges;
    private int[][] distances;
    private int[] eccentricities;
    private int[] inDegrees;  
    private int[] outDegrees;
   
    public Graph3(int numNodes) {
        this.numNodes = numNodes;
        this.numEdges = 0;
        this.adjacencyMatrix = new int[numNodes][numNodes];
        this.distances = new int[numNodes][numNodes];
        this.eccentricities  = new int[numNodes];
        this.inDegrees = new int[numNodes];
        this.outDegrees = new int[numNodes];
    }

    public void addEdge(int fromNode, int toNode, int weight) {
        if (adjacencyMatrix[fromNode - 1][toNode - 1] != 0) {
            System.out.println("Error: Edge already exists between nodes " + fromNode + " and " + toNode);
            return;
        }
       
        this.numEdges++;
        this.adjacencyMatrix[fromNode - 1][toNode - 1] = weight;
        this.outDegrees[fromNode - 1]++;
        this.inDegrees[toNode - 1]++;
    }

    public void printAdjacencyMatrix() {
        System.out.println("\nAdjacency Matrix:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print("| ");
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                    System.out.print("0 ");
                } else if (adjacencyMatrix[i][j] == 0) {
                    System.out.print("∞ ");
                } else {
                    System.out.print(adjacencyMatrix[i][j] + " ");
                }
            }
            System.out.println("|");
        }
    }

    public void displayGraphStats() { //method for Size of Graph, Order of Graph and Vertex Degrees
        // print the size of the graph, order of the graph, and vertex degrees
        System.out.println("\nSize of Graph: " + numEdges);
        System.out.println("Order of Graph: " + numNodes);
        System.out.println("\nVertex Degrees:");
        for (int i = 0; i < numNodes; i++) {
            System.out.println("Vertex " + (i + 1) + ": In-Degree = " + inDegrees[i] + ", Out-Degree = " + outDegrees[i]);
        }
    }
   
    public void displayGraphPanel() { //method for displaying Graph
        JFrame frame = new JFrame("Graph Display");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GraphPanel graphPanel = new GraphPanel(numNodes, adjacencyMatrix);
        frame.getContentPane().add(graphPanel);
        frame.pack();
        frame.setVisible(true);
    }
   
    public void displayGraphPanel2() { //method for displaying Graph
        JFrame frame = new JFrame("Graph Display");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GraphCENTER graphPanel = new GraphCENTER(numNodes, adjacencyMatrix);
        frame.getContentPane().add(graphPanel);
        frame.pack();
        frame.setVisible(true);
    }
   
    public void findAllEccentricities() { //method for Eccentricities
        if(numNodes !=0) {
            int maxDistance = 0;
            eccentricities = new int[numNodes];
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (distances[i][j] > maxDistance && distances[i][j] < 696969) {
                        maxDistance = distances[i][j];
                    }
                }
                eccentricities[i] = maxDistance;
                maxDistance = 0;
            }
       
        System.out.println("\nDistance Between Pairs:");
        for (int i = 0; i < numNodes; i++) {
             System.out.println();
            for (int j = 0; j < numNodes; j++) {
               
                if (i != j) {
                    int distance = distances[i][j];
                    if (distance == 696969) {
                        distance = 0; // Set eccentricity as 0 if there is no path between the vertices
                    }
                    System.out.println("Vertex " + (i + 1) + " to Vertex " + (j + 1) + ": " + distance);
                }
            }
        }
       
        System.out.println("\nMaximum Distances of each Vertex: ");
            for (int i = 0; i < numNodes; i++) {
                System.out.println("Vertex " + (i + 1) + ": " + eccentricities[i]);
            }
           

       
            }else {
                System.out.println("All Pair Shortest Path not initiated!");
        }
    }
   
    public void findGraphCenter() {
    GraphCENTER panel = new GraphCENTER(numNodes, adjacencyMatrix);
    int minEccentricity = Integer.MAX_VALUE;
    List<Integer> centerNode = new ArrayList<>();

    System.out.println("Eccentricities of Each Vertex:");
    for (int i = 0; i < numNodes; i++) {
        System.out.println("Vertex " + (i + 1) + ": " + eccentricities[i]);
        if (eccentricities[i] < minEccentricity) {
            minEccentricity = eccentricities[i];
            centerNode.clear();
            centerNode.add(i + 1);
        } else if (eccentricities[i] == minEccentricity) {
            centerNode.add(i + 1);
        }
    }

    if (centerNode.size() == 1) {
        System.out.println("\nEccentricity of the Graph: " + minEccentricity );
        System.out.println("Graph Center : Vertex " + centerNode.get(0));
    } else {
        System.out.println("\nGraph Centers: " + centerNode);
    }

    JFrame frame = new JFrame("Graph Display");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    GraphCENTER graphPanel = new GraphCENTER(numNodes, adjacencyMatrix);
    graphPanel  .setCenter(centerNode.get(0).intValue()); // Pass the int value
    frame.getContentPane().add(graphPanel);
    frame.pack();
    frame.setVisible(true);
}
   
    public void findAllPairsShortestPath() { //method for All Pairs Shortest Path
       
        // Initialize the distances matrix
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjacencyMatrix[i][j] == 0) {
                    distances[i][j] = 696969;
                } else {
                    distances[i][j] = adjacencyMatrix[i][j];
                }
            }
        }
       
        for(int i=0;i<numNodes;i++) {
            distances[i][i]=0;
        }
       
        // Apply Floy algorithm
        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (distances[i][k] != 696969 && distances[k][j] != 696969
                            && distances[i][k] + distances[k][j] < distances[i][j]) {
                        distances[i][j] = distances[i][k] + distances[k][j];
                    }
                }
            }
        }
   
        // Print the shortest distances between all pairs of vertices
        System.out.println("\nShortest Distances:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print("| ");
           
            for (int j = 0; j < numNodes; j++) {
                if (distances[i][j] == 696969) {
                    System.out.print("∞ ");
                } else {
                    System.out.print(distances[i][j] + " ");
                }
            }
            System.out.print(" |");
            System.out.println();
        }
    }
   
    public void findConnectivity() {
        boolean isConnected = true;
   
        for (int vertex = 0; vertex < numNodes; vertex++) {
            boolean[] visited = new boolean[numNodes];
            dfs(vertex, visited);
   
            // Check if there is a path from the current vertex to every other vertex
            for (boolean v : visited) {
                if (!v) {
                    isConnected = false;
                    break;
                }
            }
   
            if (!isConnected) {
                break;
            }
        }
   
        if (isConnected ) {
            System.out.println("\nThe graph is Connected.");
        } else {
            System.out.println("\nThe graph is Connected.");
        }
    }
   
   
    private void dfs(int vertex, boolean[] visited) {
        visited[vertex] = true;
   
        for (int i = 0; i < numNodes; i++) {
            if (adjacencyMatrix[vertex][i] != 0 && !visited[i]) {
                dfs(i, visited);
            }
        }
    }
   
   
    public static void main(String[] args) { //main method
        Scanner scanner = new Scanner(System.in);

        int numVertices = 0;
        while (numVertices <= 0 || numVertices > 25) {
            try {
                System.out.print("Enter the number of vertices: ");
                numVertices = scanner.nextInt();
                if (numVertices <= 0 || numVertices > 25) {
                    System.out.println("Error: The number of vertices should be between 1 and 25.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter an integer value.");
                scanner.nextLine(); // Clear the input buffer
            }
        }

        int numEdges;
       
        try {
            System.out.print("Enter the number of edges: ");
            numEdges = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter an integer value.");
            scanner.nextLine(); // Clear the input buffer
            numEdges = 0;
        }
       
        Graph3 graph = new Graph3(numVertices);
        for (int edgeNumber = 1; edgeNumber <= numEdges; edgeNumber++) {
            try {
                System.out.println("Enter edge " + edgeNumber);
                System.out.print("Source : ");
                int fromNode = scanner.nextInt();
                System.out.print("Destination : ");
                int toNode = scanner.nextInt();
                System.out.print("Weight : ");
                int weight = scanner.nextInt();
                graph.addEdge(fromNode, toNode, weight);

                if (edgeNumber < numEdges) {
                    System.out.println("Edge added successfully!\n");
                } else {
                    System.out.println("All edges added successfully!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter integer values for fromNode, toNode, and weight.");
                scanner.nextLine(); // Clear the input buffer
                edgeNumber--; // Retry entering the edge
            }
        }
       
        while (true) {
           
            int choice;
            System.out.println("\nTask Menu");
            System.out.println("1. Show Graph");
            System.out.println("2. Adjacency Matrix");
            System.out.println("3. Size,Order and Degree of each Vertex");
            System.out.println("4. The Connectivity of the Graph");
            System.out.println("5. All Pairs of Shortest Path");
            System.out.println("6. Eccentricities");
            System.out.println("7. Center of the Graph");
            System.out.println("8. Reset Graph");
            System.out.println("9. Exit");
           
            try {
                System.out.print("\nEnter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1-9).");
                scanner.nextLine();
                continue;
            }
           
            if (choice == 1) {
                graph.displayGraphPanel2();
               
            } else if (choice == 2) {
                graph.printAdjacencyMatrix();
           
            } else if (choice == 3) {
                graph.displayGraphStats();  
           
            } else if (choice == 4) {
                graph.findConnectivity();
               
            } else if (choice == 5) {
                graph.findAllPairsShortestPath();
               
            } else if (choice == 6) {
                graph.findAllEccentricities();
               
            } else if (choice == 7) {
                graph.findGraphCenter();
               
            } else if (choice == 8) {
                main(null);
               
            } else if (choice == 9) {
                System.out.println("Program Terminated...");
                System.exit(0);
               
            } else {
                System.out.println("Invalid choice.");
               
            }
        }
    }
}






