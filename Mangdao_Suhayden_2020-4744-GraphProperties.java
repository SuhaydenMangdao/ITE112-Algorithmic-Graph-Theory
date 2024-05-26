import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.InputMismatchException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import java.awt.BasicStroke;

//Suhayden S. Mangdao
//ITE112-IT2A.1
    
public class GraphProperty {
        // Declare class variables
        private int numbVertex;  
        private int[][] adjMatrix;  
        private int edge;
        private int [] VertexDeg;
    // Constructor for creating the adjacency matrix    
    public GraphProperty(int numberVertices) 
    {
        
        numbVertex = numberVertices;
        edge = 0;
        adjMatrix = new int[numberVertices + 1][numberVertices + 1]; 
        VertexDeg = new int [numberVertices];
        
    }
    
    //Method to add an edge between two vertices   
    public boolean Edges(int vertex1, int vertex2) 
    {
        boolean result = true;
        Scanner checks = new Scanner(System.in);
        try{
        if (this.adjMatrix[vertex1 - 1][vertex2 - 1] == 1) 
        { // error trapping check if the edge already exists
             System.out.println("Edge already exists.");
             return false;
        }
        }catch (ArrayIndexOutOfBoundsException e){
        
        System.out.println(" out of bounds");
        return false;
        };
        
        if (vertex1 == vertex2) 
        { // check if it's a self-loop
             System.out.println("Self-loops are not allowed."); // error trapping check if the efdge is the same
             return false;
        }
         
        if (vertex1 > vertex2 || vertex2 <= vertex1)
        {
            System.out.println ("out of bounds"); // error trap if edges beyond the user's input
            return false;
        }
        
        
        this.adjMatrix[vertex1 - 1][vertex2 - 1] = 1;

        this.adjMatrix[vertex2 - 1][vertex1 - 1] = 1;
        this.edge++;
        return result;
        }
    
    
    // Method to display the adjacency matrix
    public void displayMatrix() 
    {
     
        System.out.println("\nAdjacency matrix:");
           
       for (int i = 0; i < numbVertex; i++) 
        {
            int temp=i;
            if (i<9)
                System.out.print("|");
                else
                System.out.print(++temp +" |");
            for (int j = 0; j < numbVertex; j++) 
        {
            System.out.print(adjMatrix[i][j] + (" "));
        }
           System.out.print("|");
        System.out.println();
       
        }
        
    }
    
    // This Method is to display the details about the graph
    public void theGraph() 
    {
        
        int numbEdges = 1;
        for (int i = 1; i <= numbVertex; i++) {
            
            for (int j = 1; j <= numbVertex; j++) {
                
                if (adjMatrix[i][j] == 1) {
                numbEdges++;  // Print the value of each element in the adjacency matrix    
                }
                
            }
            
        }

    
        // Display the graph using a JFrame and JPanel
        JFrame frame = new JFrame("TheGraph");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize (400,500);
        JPanel panel = new IKGraph(adjMatrix, numbVertex, 50,50); // Create a new JPanel with the graph display
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        
        
    }
    
    //testing
    public void theSOD()
    {
    System.out.println("\nOrder of the graph: " + numbVertex);
            System.out.println("Size of the graph: " + (edge )); // Calculate the order of the graph
            System.out.println("\nDegree of each vertex:");
        for (int i = 0; i < numbVertex; i++) {
            int degree = 0;
            for (int j = 0; j < numbVertex; j++) {
                if (adjMatrix[i][j] == 1) {
                    degree++; // Calculate the degree of each vertex
                }
            }
            System.out.println("Vertex " + (i + 1)+ ": " + degree);
        }
        System.out.println("================================================================================");
    }
    
    // This is the main method of the program
    public static void main(String[] args) 
{
    // Create a new scanner object to read user input from the console
    Scanner check = new Scanner(System.in);

    // Initialize variables to hold the number of vertices 
    int numberVertices = 0;
    boolean validStock = false;

    while (!validStock) {
        try {
            System.out.print("Enter The number of vertices of graph (maximum of 25): ");
            numberVertices = check.nextInt();
            //System.out.print("==============================================================================");
            
            if (numberVertices < 1 || numberVertices > 25) {
                throw new IllegalArgumentException("Invalid Input: The Number of vertices must be between 1 and 25.");
            }
            validStock = true;
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input..... Please enter an integer.");
            check.nextLine();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create a new graph object with the specified number of vertices
    GraphProperty graph = new GraphProperty(numberVertices);

    System.out.print("Enter the number of Edges: ");
    int loop = check.nextInt();
    System.out.println("==================================================================================");
    
    for(int i = 0; i < loop; i++) {
        System.out.print("Input source node: ");
        int vertex1 = check.nextInt();
         System.out.print("Input destination node: ");
        int vertex2 = check.nextInt();
        if (!graph.Edges(vertex1, vertex2)) {
            i -= 1;
        }
        System.out.println("==================================================================================");
    }

    while (true) {
        System.out.println("\nChoose an option:");
        System.out.println("1. Display the Adjacency Matrix");
        System.out.println("2. Show the Graph");
        System.out.println("3. Size, Order, Degree");
        System.out.println("4. Enter Another Graph Data");
        System.out.println("5. Exit");
        System.out.println("==================================================================================");

        System.out.print("\nEnter your option: ");

        int option = check.nextInt();

        if (option == 1) {
            graph.displayMatrix();
            System.out.println("====================================================================================");
        } else if (option == 2) {
            graph.theGraph();
            System.out.println("====================================================================================");
        } else if (option == 3) {
            graph.theSOD();
        } else if (option == 4) {
            while (true) {
                try {System.out.print("Enter the number of vertices in the graph (at most 25): ");
                    numberVertices = check.nextInt();
                    if (numberVertices < 1 || numberVertices > 25) {
                        throw new IllegalArgumentException("Error: Number of vertices must be between 1 and 25.");
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter an integer.");
                    check.nextLine();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            graph = new GraphProperty(numberVertices);
            
            System.out.print("Enter the number of Edges: ");
            loop = check.nextInt();

            for (int i = 0; i < loop; i++) {
                System.out.print("Input source node: ");
                int vertex1 = check.nextInt();
                System.out.print("Input destination node: ");
                int vertex2 = check.nextInt();
                if (!graph.Edges(vertex1, vertex2)) {
                    i -= 1;
                }
                System.out.println("==================================================================================");
            }
            
        } else if (option == 5) {
            System.out.println("Have A Good Day!");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }
}
                    
    public class IKGraph extends JPanel {
    
        private int[][] adjacencyMatrix;
        private int numVertices;
        private int radius;
        private int vertexSize;
        private Point[] vertices;
        private boolean[] isDragging;
        private int draggingIndex = -1;
        
       public IKGraph(int[][] adjacencyMatrix, int numVertices, int radius, int vertexSize) 
        {
            this.adjacencyMatrix = adjacencyMatrix;
            this.numVertices = numVertices;
            this.radius = radius;
            this.vertexSize = vertexSize;
            this.vertices = new Point[numVertices];
            this.isDragging = new boolean[numVertices];
    
            setPreferredSize(new Dimension(600, 600));
    
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (draggingIndex != -1) {
                        vertices[draggingIndex] = e.getPoint();
                        repaint();
                    }
                }
            });
    
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    // check if any vertex is being clicked
                    for (int i = 0; i < numVertices; i++) {
                        if (vertices[i].distance(evt.getPoint()) < vertexSize/2) {
                            isDragging[i] = true;
                            draggingIndex = i;
                            break;
                        }
                    }
                }
    
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    // stop dragging the vertex
                    if (draggingIndex != -1) {
                        isDragging[draggingIndex] = false;
                        draggingIndex = -1;
                    }
                }
            });
        }
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g); // Calls the superclass's paintComponent method to clear the panel and prepare it for new painting
            Graphics2D g2d = (Graphics2D) g; // Casts the Graphics object to a Graphics2D
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON); // Enables antialiasing which smooths out jagged edges and makes shapes and text
            int centerX = getWidth() / 2; // get the coordinates to position the graph
            int centerY = getHeight() / 2; // get the coordinates to position the graph
            
             // draw vertices
            for (int i = 0; i < numVertices; i++) 
                {
                    double angle = 2 * Math.PI * i / numVertices; // angle for each vertex
                    int x = (int) (centerX + radius * Math.cos(angle)); // x coordinate of vertex using radius and angle
                    int y = (int) (centerY + radius * Math.sin(angle)); // y coordinate of vertex using radius and angle
        
                    if (vertices[i] == null) {
                            vertices[i] = new Point(x, y);
                        }
    
                    g.setColor(Color.BLUE); // set the color to blue
                    g.fillOval(vertices[i].x - vertexSize/2, vertices[i].y - vertexSize/2, vertexSize, vertexSize); // draw the oval/circle of vertex
                    g.setFont(new Font("Arial", Font.BOLD, 20)); // set the font to Arial 6
                    g.setColor(Color.WHITE); // label the vertex inside of the circle
                    g.drawString("" + (i + 1), vertices[i].x - 6, vertices[i].y + 6);
                    g.setColor(Color.BLUE); // set the color to blue
            }
                 // draw edges
                    g2d.setStroke(new BasicStroke(3)); // set the stroke to 3 pixels
                    g.setColor(Color.BLACK); // set a black lines to show the edges
        
                // connect vertex and edges using loop
            for (int i = 0; i < numVertices; i++) 
            {
                for (int j = i + 1; j < numVertices; j++) 
                {
                    if (adjacencyMatrix[i][j] == 1) 
                    {
                    // get the coordinates of the vertices that form the edge
                    int x1 = vertices[i].x;
                    int y1 = vertices[i].y;
                    int x2 = vertices[j].x;
                    int y2 = vertices[j].y;
    
                    g.drawLine(x1, y1, x2, y2);
                    }
                }
            }
        // draw vertices
            for (int i = 0; i < numVertices; i++) 
                {
                    
                    
                    g.setColor(Color.BLUE); // set the color to BLUE
                    g.fillOval(vertices[i].x - vertexSize/2, vertices[i].y - vertexSize/2, vertexSize, vertexSize); // draw the oval/circle of vertex
                    g.setFont(new Font("Arial", Font.BOLD, 20)); // set the font to Arial 6
                    g.setColor(Color.WHITE); // label the vertex inside of the circle
                    g.drawString("" + (i + 1), vertices[i].x - 6, vertices[i].y + 6);
                    g.setColor(Color.BLUE); // set the color to blue
            }
    
               
        }
    
            public void setDragging(int vertexIndex, boolean isDragging) {
                this.isDragging[vertexIndex] = isDragging;
            }
        
         
    }
} 

    


