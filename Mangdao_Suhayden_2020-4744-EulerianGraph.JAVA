//SUHAYDEN S. MANGDAO
//ITE 112 - IT2N.1
//JUNE 29, 2023
//PROGRAM #2

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class Graph2 {
    private int V; // number of vertices
    private int[][] adjMatrix; // adjacency matrix
    private boolean[] usedVertex;
    private JFrame activeFrame;
    private GraphVisualization graph;
    private JFrame pathFrame;
    private JFrame circuitFrame;
    private GraphVisualization path;
    private GraphVisualization circuit;

    public Graph2(int v){
        this.V = v;
        adjMatrix = new int[v][v];
        usedVertex = new boolean[v];
    }

    // add an edge to the graph
    public void addEdge(int u, int v) {
        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1;
        if(activeFrame != null){
            graph.setAdjacencyMatrix(adjMatrix);
        }
    }

    public void displayAdjMatrix(){
        System.out.println("=================================== \n");
        System.out.println("Adjacency Matrix:");
        for (int i = 0; i < V; i++) {
            System.out.print("| ");
            for (int j = 0; j < V; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println("|");
           
        }
         System.out.println("=================================== \n");
    }

    public void displayGraph(){
        JFrame frame = new JFrame("Graph Visualization");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graph = new GraphVisualization(adjMatrix, Color.black);
        frame.add(graph);
        frame.setVisible(true);
        if(activeFrame != null){
            activeFrame.dispose();
        }
        activeFrame = frame;
    }

    public void displayPath(){
        JFrame frame = new JFrame("Euler Path");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        path = new GraphVisualization(adjMatrix, Color.red);
        frame.add(path);
        frame.setVisible(true);
        if(pathFrame != null){
            pathFrame.dispose();
        }
        pathFrame = frame;
    }

    public void displayCircuit(){
        JFrame frame = new JFrame("Euler Circuit");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        circuit = new GraphVisualization(adjMatrix, Color.green);
        frame.add(circuit);
        frame.setVisible(true);
        if(circuitFrame != null){
            circuitFrame.dispose();
        }
        circuitFrame = frame;
    }

    // display the degree of each vertex
    public void displayDegree() {
        System.out.println("=================================== \n");
        System.out.println("Degree of each vertex:");
        for (int i = 0; i < V; i++) {
            int degree = 0;
            for (int j = 0; j < V; j++) {
                degree += adjMatrix[i][j];
            }
            System.out.println("Vertex " + (i+1) + ": " + degree);
        }
        System.out.println("=================================== \n");
    }

    public void displaySizeOrder() {
        System.out.println("=================================== \n");
        int size = 0;
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                size += adjMatrix[i][j];
            }
        }
        System.out.println("Size: " + size);
        System.out.println("Order: " + V);
        System.out.println("=================================== \n");
    }

    
    
    public int isEulerian() {
        if (!isConnected() || V == 1) {
            return 0; // non-Eulerian
        }
        int oddDegreeVertices = 0;
        for (int i = 0; i < V; i++) {
            int degree = 0;
            for (int j = 0; j < V; j++) {
                degree += adjMatrix[i][j];
            }
            if (degree % 2 == 1) {
                oddDegreeVertices++;
            }
        }
        if (oddDegreeVertices == 0) {
            return 1; // Euler circuit (all even degrees)
        } else if (oddDegreeVertices == 2 || oddDegreeVertices == 1) {
            return 2; // Euler path (exactly 2 odd degrees)
        } else {
            return 0; // non-Eulerian (more than 2 odd degrees)
        }
    }
    
    
    public int isSemiEulerian() {
    if (!isConnected() || V == 1) {
        return 0; // non-semi-Eulerian
    }
    int oddDegreeVertices = 0;
    for (int i = 0; i < V; i++) {
        int degree = 0;
        for (int j = 0; j < V; j++) {
            degree += adjMatrix[i][j];
        }
        if (degree % 2 == 1) {
            oddDegreeVertices++;
        }
    }
    if (oddDegreeVertices == 0 || oddDegreeVertices == 2) {
        return 1; // semi-Eulerian (0 or 2 odd degrees)
    } else {
        return 0; // non-semi-Eulerian (more than 2 odd degrees)
    }
    }

    public boolean isConnected() {
        boolean[] visited = new boolean[V];
        dfs(adjMatrix, 0, visited);
        for (boolean vertexVisited : visited) {
            if (!vertexVisited) {
                return false;
            }
        }
        return true;
    }

    private static String dfs(int[][] adjacencyMatrix, int vertex, boolean[] visited) {
        visited[vertex] = true;
        StringBuilder component = new StringBuilder(vertex + " ");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                component.append(dfs(adjacencyMatrix, i, visited));
            }
        }
        return component.toString();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while(true) {
            int vertexCount;
            boolean validVertexCondition = false;
            do {
                System.out.print("How many vertices:");
                String strVertexCount = s.next();
                while (!inputErrorTest(strVertexCount)) {
                    System.out.println("Invalid input. Please try again.");
                    strVertexCount = s.next();
                }
                vertexCount = Integer.parseInt(strVertexCount);
                if (vertexCount <= 25) {
                    validVertexCondition = true;
                } else System.out.println("Make sure vertex count is less than or equal to 25.");
            } while (!validVertexCondition);
            Graph2 g = new Graph2(vertexCount);

            int initialVertex;
            int finalVertex;
            int numberOfEdges;
            int maxEgdes = (vertexCount * (vertexCount - 1)) / 2;
            boolean inputVertex = true;
            do {
                System.out.print("How many edges:");
                String strEdgeCount = s.next();
                while (!inputErrorTest(strEdgeCount)) {
                    System.out.println("Invalid input. Please try again.");
                    strEdgeCount = s.next();
                }
                numberOfEdges = Integer.parseInt(strEdgeCount);
                System.out.println(numberOfEdges);
                if (numberOfEdges <= maxEgdes) {
                    validVertexCondition = true;
                } else {
                    System.out.println("Make sure edge count is less than or equal to " + maxEgdes + ".");
                    validVertexCondition = false;
                }
            } while (!validVertexCondition);
            if (numberOfEdges > 0) {
                do {
                    validVertexCondition = false;
                    do {
                        System.out.println("Choose an initial vertex:");
                        String strInitialVertex = s.next();
                        while (!inputErrorTest(strInitialVertex)) {
                            System.out.println("Invalid input. Please try again.");
                            strInitialVertex = s.next();
                        }
                        initialVertex = Integer.parseInt(strInitialVertex) - 1;

                        if (initialVertex < vertexCount && initialVertex >= 0) {
                            validVertexCondition = true;
                            g.usedVertex[initialVertex] = true;
                        } else System.out.println("Make sure the selected vertex exists.");
                    } while (!validVertexCondition);
                    validVertexCondition = false;
                    do {
                        System.out.println("Choose an final vertex:");
                        String strFinalVertex = s.next();
                        while (!inputErrorTest(strFinalVertex)) {
                            System.out.println("Invalid input. Please try again.");
                            strFinalVertex = s.next();
                        }
                        finalVertex = Integer.parseInt(strFinalVertex) - 1;
                        if (finalVertex < vertexCount && finalVertex >= 0) {
                            validVertexCondition = true;
                            g.usedVertex[finalVertex] = true;
                        } else System.out.println("Make sure the selected vertex exists.");
                    } while (!validVertexCondition);
                    if (g.adjMatrix[finalVertex][initialVertex] == 0) {
                        if (finalVertex == initialVertex) {
                            System.out.println("That is not allowed.");
                            numberOfEdges = numberOfEdges + 1;
                        } else {
                            g.addEdge(initialVertex, finalVertex);
                        }
                    } else {
                        System.out.println("That already exists.");
                        numberOfEdges = numberOfEdges + 1;
                    }
                    for (int i = 0; i < g.V; i++) {
                        if (!g.usedVertex[i]) {
                            if (i > 0) {
                                System.out.println("The following vertex/vertices haven't been used: " + (i+1));
                            }
                        }
                    }
                    numberOfEdges = numberOfEdges - 1;

                    if (numberOfEdges == 0) {
                        inputVertex = false;
                    }
                } while (inputVertex);
            }

            do {
                int choice = selectOption();

                if (choice == 1) {
                    g.displayAdjMatrix();
                } else if (choice == 2) {
                    g.displaySizeOrder();
                } else if (choice == 3) {
                    g.displayDegree();
                } else if (choice == 4) {
                    g.displayGraph();
                } else if (choice == 5) {
                    System.out.println("=================================== \n");
                    //System.out.println("The graph is " + (g.isConnected() ? "connected because in each pair of vertices, there is one component.." : "disconnected because there are 2 or more components"));
                    System.out.println("The graph is " + (g.isConnected() ? "connected. As it's no. of component is 1" : "disconnected. As it's no. of components is 2"));

                    System.out.println("");
                    System.out.println("THE SCANNED COMPONENTS ARE: ");
                    System.out.println(" ");
                    showConnectedComponents(g.adjMatrix);
                    System.out.println();
                    System.out.println("=================================== ");
                } else if (choice == 6) {
                System.out.println("=================================== \n");
                System.out.print("The graph is ");
                
                if (g.isEulerian() == 1) {
                //System.out.println("eulerian because it have an even degrees");
                //System.out.println("eulerian because it has an even degrees w/ 1 component");
                System.out.println("eulerian because it is connected and all vertex have even degrees");
                System.out.println("===================================\n");
                } // the og graph 
                
                /*else if (g.isEulerian() == 0) {
                System.out.println("eulerian because it has 2 odd vertices w/ 1 component");
                System.out.println("===================================\n");
                }*/
                
                else if (g.isEulerian() == 2) {
                //System.out.println("semi-eulerian because it has 2 odd vertices w/ 1 component");
                System.out.println("semi-eulerian because there are 2 odd degree vertices");
                System.out.println("===================================\n");
                }
                else if (g.isSemiEulerian() == 1) {
                System.out.println("semi-eulerian because it has 2 components with odd-degree vertices:");
                System.out.println("===================================\n");
                }
                else {
                //System.out.println("not eulerian because the graph is disconnected.                                                                                                       It also cannot be considered as semi-eulerian.\n");
                System.out.println("not eulerian because the graph has 2 or more nodes that has odd degrees"); 
                System.out.println("===================================");
                }
                } 
                
                
                
                else if (choice == 7) {
                    //if (g.isSemiEulerian() == 0) {
                         //if (g.isEulerian() == 1 || g.isEulerian() == 2) {
                        if (g.isEulerian() == 1) {
                        //System.out.println("=================================== \n");
                        //System.out.println("The graph has a Euler path");
                        //System.out.println("=================================== \n");

                        int[][] newMatrix = new int[g.adjMatrix.length][g.adjMatrix.length];
                        // Copy the values from the old matrix to the new matrix
                        for (int i = 0; i < g.adjMatrix.length; i++) {
                            for (int j = 0; j < g.adjMatrix.length; j++) {
                                newMatrix[i][j] = g.adjMatrix[i][j];
                            }
                        }
                        

                        int[] path = findEulerTrail(newMatrix);
                        
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        
                        System.out.println("=====================================");
                        //System.out.println("The graph has Euler path\n");
                        //System.out.println("The graph has no Euler path because there is no odd degree vertices");
                        //System.out.println("The graph has Euler path because there is no odd degree vertices");
                        //System.out.println("The graph has Euler path because there is no odd degree vertices");
                        System.out.println("The graph has eulerian path because it is connected and all vertex have even degrees");
                        //eulerian because it is connected and all vertex have even degrees
                        //System.out.println("The graph has no Euler path because it has separate components.");
                        System.out.print("Euler path: [");
                        for(int i = 0; i < path.length; i++){
                            //System.out.print((path[i]+1) + (i < path.length - 1? "->", ":"]"));
                            System.out.print((path[i]+1) + (i < path.length - 1? " -> " : "] \n" ));
                        }
                        System.out.println("=====================================");
                    } //og graph
                    else if (g.isSemiEulerian() == 2) {
                        //System.out.println("=================================== \n");
                        //System.out.println("The graph has a Euler path");
                        //System.out.println("=================================== \n");

                        int[][] newMatrix = new int[g.adjMatrix.length][g.adjMatrix.length];
                        // Copy the values from the old matrix to the new matrix
                        for (int i = 0; i < g.adjMatrix.length; i++) {
                            for (int j = 0; j < g.adjMatrix.length; j++) {
                                newMatrix[i][j] = g.adjMatrix[i][j];
                            }
                        }

                        int[] path = findEulerTrail(newMatrix);
                        
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        
                        System.out.println("=====================================");
                        System.out.println("The graph has Euler path because there are two vertices with odd degrees");
                        g.displayPath();
                        //System.out.println("The graph has no Euler path because it has separate components.");
                        /*System.out.print("Euler path: [");
                        for(int i = 0; i < path.length; i++){
                            System.out.print((path[i]+1) + (i < path.length - 1? ", ":"]\n"));
                        }*/
                        System.out.println("=====================================");
                        //System.out.println();
                        //g.displayPath();
                    }
                    /*else if (g.isEulerian() == 0) { 
                        //System.out.println("=================================== \n");
                        //System.out.println("The graph has a Euler path");
                        //System.out.println("=================================== \n");
                        
                        System.out.println("=====================================");
                        //System.out.println("The graph has Euler path\n");
                        System.out.println("The graph has no Euler path because there are vertices with 0 degree");
                        //System.out.println("The graph has no Euler path because it has separate components.");
                        /*System.out.print("Euler path: [");
                        for(int i = 0; i < path.length; i++){
                            System.out.print((path[i]+1) + (i < path.length - 1? ", ":"]\n"));
                        }*/
                        //System.out.println("=====================================");
                        //System.out.println();
                        //g.displayPath();
                    //}
                    else if (g.isSemiEulerian() == 1) {
                        System.out.println("=================================== \n");
                        System.out.println("The graph has a Euler path");
                        System.out.println("=================================== \n");

                        int[][] newMatrix = new int[g.adjMatrix.length][g.adjMatrix.length];
                        // Copy the values from the old matrix to the new matrix
                        for (int i = 0; i < g.adjMatrix.length; i++) {
                            for (int j = 0; j < g.adjMatrix.length; j++) {
                                newMatrix[i][j] = g.adjMatrix[i][j];
                            }
                        }

                        int[] path = findEulerTrail(newMatrix);
                        
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        System.out.println(" ");
                        
                        System.out.println("=====================================");
                        //System.out.println("The graph has Euler path\n");
                        System.out.println("The graph has Euler path because it has two distinct vertices with even degrees.");
                        System.out.print("Euler path: [");
                        for(int i = 0; i < path.length; i++){
                            //System.out.print((path[i]+1) + (i < path.length - 1? ", ":"]\n"));
                            System.out.print((path[i]+1) + (i < path.length - 1? " -> ":"]\n"));

                        }
                        System.out.println("=====================================");
                        System.out.println();
                        g.displayPath();
                    }
                    else {
                        System.out.println("=================================== \n");
                        //System.out.println("The graph has no Euler path because it has two distinct vertices with odd degrees.");
                        //System.out.println("The graph has no Euler path because it has 2 or more separate components");
                        //System.out.println("The graph is disconnected, no eulerian path");
                        //System.out.println("The graph is disconnected, no eulerian path");
                        System.out.println("The graph has no Euler path because it has two distinct vertices with odd degrees.");
                        System.out.println("=================================== \n");
                        //g.displayPath();
                    }
                } else if (choice == 8) {
                    //if (g.isEulerian() == 1 && g.isEulerian() == 2) {
                        //if (g.isEulerian() == 1 && g.isEulerian() == 2) {
                       if (g.isEulerian() == 1) {
                        System.out.println("=================================== \n");
                        //System.out.println("The graph has a Euler circuit because all vertices have an even degree.");
                        int[][] newMatrix = new int[g.adjMatrix.length][g.adjMatrix.length];
                        // Copy the values from the old matrix to the new matrix
                        for (int i = 0; i < g.adjMatrix.length; i++) {
                            for (int j = 0; j < g.adjMatrix.length; j++) {
                                newMatrix[i][j] = g.adjMatrix[i][j];
                            }
                            
                        //System.out.println("=================================== \n");
                        }
                        int[] path = findEulerTrail(newMatrix);
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("=================================== \n");
                        System.out.println("The graph has a Eulerian circuit because all vertices have an even degree.");
                        System.out.print("Euler circuit: [");
                        for (int i = 0; i < path.length; i++) {
                            //System.out.print((path[i]+1) + (i < path.length - 1? ", " : "]"));
                            System.out.print((path[i]+1) + (i < path.length - 1? " -> " : "]"));
                        }
                        System.out.println();
                        System.out.println("=================================== \n");
                        g.displayCircuit();
                    } 
                     
                    else if (g.isSemiEulerian() == 2) {
                        System.out.println("=================================== \n");
                        //System.out.println("The graph has a Euler circuit because all vertices have an even degree.");
                        int[][] newMatrix = new int[g.adjMatrix.length][g.adjMatrix.length];
                        // Copy the values from the old matrix to the new matrix
                        for (int i = 0; i < g.adjMatrix.length; i++) {
                            for (int j = 0; j < g.adjMatrix.length; j++) {
                                newMatrix[i][j] = g.adjMatrix[i][j];
                            }
                            
                        //System.out.println("=================================== \n");
                        }
                        int[] path = findEulerTrail(newMatrix);
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("=================================== \n");
                        System.out.println("The graph has no Eulerian circuit because there are odd degree vertices");
                        //System.out.println("Graph is disconnected, no Eulerian Circuit");
                        System.out.println("=================================== \n");
                        
                    } 
                    
                    else if (g.isSemiEulerian() == 1) { 
                       
                        System.out.println("=================================== \n");
                        //System.out.println("The graph has a Euler circuit because all vertices have an even degree.");
                        int[][] newMatrix = new int[g.adjMatrix.length][g.adjMatrix.length];
                        // Copy the values from the old matrix to the new matrix
                        for (int i = 0; i < g.adjMatrix.length; i++) {
                            for (int j = 0; j < g.adjMatrix.length; j++) {
                                newMatrix[i][j] = g.adjMatrix[i][j];
                            }
                            
                        //System.out.println("=================================== \n");
                        }
                        int[] path = findEulerTrail(newMatrix);
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("=================================== \n");
                        System.out.println("The graph has no eulerian circuit because there are odd degree vertices.");
                       
                        System.out.println("=================================== \n");
                    } 
                        else if (g.isSemiEulerian() == 0) {
                       
                        System.out.println("=================================== \n");
                        //System.out.println("The graph has a Euler circuit because all vertices have an even degree.");
                        int[][] newMatrix = new int[g.adjMatrix.length][g.adjMatrix.length];
                        // Copy the values from the old matrix to the new matrix
                        for (int i = 0; i < g.adjMatrix.length; i++) {
                            for (int j = 0; j < g.adjMatrix.length; j++) {
                                newMatrix[i][j] = g.adjMatrix[i][j];
                            }
                            
                        //System.out.println("=================================== \n");
                        }
                        int[] path = findEulerTrail(newMatrix);
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("=================================== \n");
                        System.out.println("The graph has no Eulerian circuit because there are odd degree vertices.");
                        //System.out.println("Graph is disconnected, no Eulerian circuit");
                        System.out.println("=================================== \n");
                    } 
                    
                    else {
                        System.out.println("=================================== \n");
                        //System.out.println("The graph has no Euler circuit because the graph is not connected or some vertices does not have an even degree.");
                        //System.out.println("The graph has eulerian circuit because it has two vertices of odd degree");
                        
                //(the og code) String message = (g.isEulerian() == 0 ? "The graph has no Euler circuit because the graph is not connected or some vertices do not have an even degree." : "The graph has eulerian circuit ");
                 {    
                System.out.println("=================================== \n");
                System.out.print("The graph ");
                if (g.isEulerian() == 1) {
                System.out.println("has eulerian circuit.");
    
                System.out.println("===================================\n");
                } else if (g.isSemiEulerian() == 1) {
                //System.out.println("has semi-eulerian circuit");
                System.out.println("has no eulerian circuit, because there are odd degree vertices");
                System.out.println("===================================\n");
                g.displayCircuit();
                
                } else {
                System.out.println("doesn't have eulerian circuit because the graph is disconnected \n");
                System.out.println("===================================");
                }
                
                
                
                }
                        
                        //System.out.println(message);
                        //g.displayCircuit();
                System.out.println("=================================== \n");
                    }
                } else { // choice == 9
                    if (g.activeFrame != null) {
                        g.activeFrame.dispose();
                    }
                    if (g.pathFrame != null) {
                        g.pathFrame.dispose();
                    }
                    if (g.circuitFrame != null) {
                        g.circuitFrame.dispose();
                    }
                    break;
                }
            } while (true);

            String answer;
            s = new Scanner(System.in);
            do {
                System.out.print("Do you want to continue program? (yes/no): ");
                answer = s.nextLine().toLowerCase();
            }while(!(answer.equals("yes") || answer.equals("no")));
            if(answer.equals("no")){
                break;
            }
        }
    }

    public static void showConnectedComponents(int[][] adjacencyMatrix) {
        boolean[] visited = new boolean[adjacencyMatrix.length];
        int componentCount = 0;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                componentCount++;
                //System.out.println("There is only " + componentCount + " component");
                System.out.println("COMPONENT " + componentCount + ":");
                String component = dfs(adjacencyMatrix, i, visited);
                System.out.println(component);
            }
        }
    }


    public static int selectOption(){
        Scanner s = new Scanner(System.in);
        System.out.println("Choose what to display: ");
        System.out.println("1. The adjacency matrix of the graph");
        System.out.println("2. The size and order of graph G(V,E)");
        System.out.println("3. The degree of each vertex");
        System.out.println("4. The Graph of G");
        System.out.println("5. The connectivity of the graph G(V,E)");
        System.out.println("6. The Eulerianity of graph");
        System.out.println("7. The Euler path");
        System.out.println("8. The Eulerian circuit.");
        System.out.println("9. Exit");
        System.out.println();

        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = s.nextInt();
        }while(choice < 1 || choice > 9);
        return choice;
    }

    public static boolean inputErrorTest(String input){
        if(isNumeric(input)){
            if (!input.contains("-") && !input.contains(".")){
                return true;
            }else return false;
        }
        else return false;
    }
    private static boolean isNumeric(String str){
        try{
            int num = Integer.parseInt(str);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public static int countEdges(int[][] adjMatrix) {
        int numEdges = 0;
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix[i].length; j++) {
                if (adjMatrix[i][j] == 1) {
                    numEdges++;
                }
            }
        }
        return numEdges;
    }

    // Returns the starting vertex for the algorithm
    private static int findStartVert(int[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            int deg = 0;
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] == 1) {
                    deg++;
                }
            }
            if (deg % 2 == 1) {
                return i;
            }
        }
        return 0;
    }

    // Checks if an edge is a bridge or not
    private static boolean isBridge(int[][] graph, int u, int v) {
        int deg = 0;
        for (int i = 0; i < graph.length; i++) {
            if (graph[v][i] == 1) {
                deg++;
            }
        }
        if (deg > 1) {
            return false;
        }
        return true;
    }

    // Main Fleury's algorithm
    private static void fleuryAlgorithm(int[][] graph, int start, List<Integer> path, int numEdges) {
        for (int v = 0; v < graph.length; v++) {
            if (graph[start][v] == 1 && (numEdges <= 1 || !isBridge(graph, start, v))) {
                // Remove edge from the graph
                graph[start][v] = 0;
                graph[v][start] = 0;
                path.add(v);
                numEdges--;

                fleuryAlgorithm(graph, v, path, numEdges);
            }
        }
        for(int i = 0; i < graph.length; i++){
            for(int j = 0; j < graph.length; j++){
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns the Euler circuit path as an array
    public static int[] findEulerTrail(int[][] graph) {
        List<Integer> path = new ArrayList<Integer>();
        int numEdges = countEdges(graph);
        int start = findStartVert(graph);
        path.add(start);

        fleuryAlgorithm(graph, start, path, numEdges);

        // Convert list to array
        int[] circuit = new int[path.size()];
        for (int i = 0; i < path.size(); i++) {
            circuit[i] = path.get(i);
        }
        return circuit;
    }
}

class GraphVisualization extends JPanel {
    private final Color edgeColor;
    private int[][] adjacencyMatrix;

    private int selectedVertexIndex = -1;
    private int dragStartX, dragStartY;
    double[] xCoords;
    double[] yCoords;
    boolean firstCreate;

    public GraphVisualization(int[][] adjacencyMatrix, Color color) {
        this.edgeColor = color;
        this.adjacencyMatrix = adjacencyMatrix;
        this.xCoords = new double[adjacencyMatrix.length];
        this.yCoords = new double[adjacencyMatrix.length];
        this.firstCreate = true;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                selectVertex(e.getX(), e.getY());
                dragStartX = e.getX();
                dragStartY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (selectedVertexIndex != -1) {
                    moveVertex(e.getX() - dragStartX, e.getY() - dragStartY);
                    selectedVertexIndex = -1;
                    repaint();
                }
            }
        });
    }

    public void setAdjacencyMatrix(int[][] m){
        adjacencyMatrix = m;
        repaint();
    }

    public void spawnVertices(){
        // Calculate dimensions of the panel
        int width = getWidth();
        int height = getHeight();

        // Calculate coordinates for vertices
        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double radius = Math.min(centerX, centerY) * 0.8;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            double angle = 2 * Math.PI * i / adjacencyMatrix.length;
            xCoords[i] = centerX + radius * Math.cos(angle);
            yCoords[i] = centerY + radius * Math.sin(angle);
        }
    }

    public void selectVertex(int x, int y) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            Ellipse2D vertex = new Ellipse2D.Double(xCoords[i] - 20, yCoords[i] - 20, 40, 40);
            if (vertex.contains(x, y)) {
                selectedVertexIndex = i;
                break;
            }
        }
    }

    private void moveVertex(int dx, int dy) {
        xCoords[selectedVertexIndex] += dx;
        yCoords[selectedVertexIndex] += dy;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(firstCreate){
            spawnVertices();
            firstCreate = false;
        }

        Graphics2D g2d = (Graphics2D) g.create();

        // Set rendering hints for antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set stroke width
        g2d.setStroke(new BasicStroke(2));

        // Set font for vertex labels
        g2d.setFont(g2d.getFont().deriveFont(14f));

        // Draw edges
        g2d.setColor(this.edgeColor);
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    Line2D edge = new Line2D.Double(xCoords[i], yCoords[i], xCoords[j], yCoords[j]);
                    g2d.draw(edge);
                }
            }
        }

        // Draw vertices
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            Ellipse2D vertex = new Ellipse2D.Double(xCoords[i] - 20, yCoords[i] - 20, 40, 40);
            g2d.setPaint(Color.YELLOW);
            g2d.fill(vertex);
            g2d.setPaint(Color.BLACK);
            g2d.draw(vertex);
            g2d.drawString(Integer.toString(i+1), (float) xCoords[i] - 5, (float) yCoords[i] + 5);
        }

        g2d.dispose();
    }
}



